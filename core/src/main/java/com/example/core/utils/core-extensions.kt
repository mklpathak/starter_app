package com.example.core.utils


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import java.util.*



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