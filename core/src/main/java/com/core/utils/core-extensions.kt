package com.core.utils


import com.core.ModelTypes
import com.core.models.BaseModel
import com.core.models.BaseModelWrapper
import com.core.models.ErrorModel
import com.core.models.LoadingModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select


fun <T> T.convertToBaseModel(itemType: Int) : BaseModelWrapper<T> {
    return BaseModelWrapper(this,itemType)
}


fun <T : BaseModel> Result<T>.convertToResource() : Resource<T> {

    return when (this){
        is Result.Success ->{
                Resource.success(this.data)
        }
        is Result.Error -> {
            Resource.error(ErrorModel(this.error))
        }
        is Result.Loading-> {
            Resource.loading()
        }
    }
}



fun <T> Result<T>.convertToBaseModel( data: (T) -> BaseModel) : BaseModel {

    return when (this){
        is Result.Success ->{
            this.data?.let {
                data(it)
            }?: ErrorModel()
        }
        is Result.Error -> {
            ErrorModel()
        }
        is Result.Loading-> {
            LoadingModel()
        }
    }
}


fun <T> Result<T>.convertToResource(itemType: Int) : Resource<BaseModelWrapper<T>> {

    return when (this){
        is Result.Success ->{
            this.data?.convertToBaseModel(itemType)?.let {
                Resource.success(it)
            }?: Resource.error(ErrorModel())
        }
        is Result.Error -> {
            Resource.error(ErrorModel(this.error))
        }
        is Result.Loading-> {
            Resource.loading()
        }
    }
}

fun <T> Flow<T>.throttleFirstJava(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                emit(value)
            }
        }
    }
}


@ExperimentalCoroutinesApi
fun <T> Flow<T>.throttleLatestKotlin(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }

    return channelFlow {
        val done = Any()
        val values = produce(capacity = Channel.CONFLATED) {
            collect { value -> send(value) }
        }

        var lastValue: Any? = null
        val ticker = Ticker(periodMillis)
        while (lastValue !== done) {
            select<Unit> {
                values.onReceiveOrNull {
                    if (it == null) {
                        ticker.cancel()
                        lastValue = done
                    } else {
                        lastValue = it
                        if (!ticker.isStarted) {
                            ticker.start(this@channelFlow)
                        }
                    }

                }

                ticker.getTicker().onReceive {
                    if (lastValue !== null) {
                        val value = lastValue
                        lastValue = null
                        send(value as T)
                    } else {
                        ticker.stop()
                    }
                }
            }
        }
    }
}



inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable?) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType?) -> Boolean = { true }
) = flow<Result<ResultType>> {
    emit(Result.Loading())
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(Result.Loading())

        try {
            saveFetchResult(fetch())
            query().map { Result.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Result.Error(throwable, it) }
        }
    } else {
        query().map { Result.Success(it) }
    }
    emitAll(flow)

}


inline fun <reified T,A> instantCombineWithTag(vararg flows: Pair<A,Flow<T>>) = channelFlow {
    flows.forEachIndexed { index, flow ->
        launch {
            flow.second.collect { emittedElement ->
                send(flow.first to emittedElement)
            }
        }
    }
}

 fun <T> Flow<Result<T>>.convertToBaseModelFlow(data: (T) -> BaseModel) : Flow<BaseModel> {


     return channelFlow<BaseModel> {
         launch {
             this@convertToBaseModelFlow.collect {
                 when (it) {
                     is Result.Success-> {
                         it.data?.let{
                             send(data(it))
                         }?:send(ErrorModel())
                     }
                     is Result.Loading->{
                         send(LoadingModel())
                     }
                     is Result.Error->{
                         send(ErrorModel(throwable = it.error))
                     }
                 }
             }
         }
     }
}