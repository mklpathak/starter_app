package com.example.samplemovieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.samplemovieapp.database.PopularDao
import com.example.samplemovieapp.models.BaseModel
import com.example.samplemovieapp.network.ApiHelper
import com.example.samplemovieapp.ui.home.HomeViewModel
import com.example.samplemovieapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SingleNetworkCallViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var databaseHelper: PopularDao

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<BaseModel>>>

    @Mock
    private lateinit var repository: MainRepository

    @Before
    fun setUp() {
        repository  =  MainRepository(apiHelper,databaseHelper)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<BaseModel>())
                .`when`(apiHelper)
                .getNowPlaying()
            doReturn(emptyList<BaseModel>())
                .`when`(apiHelper)
                .getPopularMovies()
            doReturn(emptyList<BaseModel>())
                .`when`(apiHelper)
                .getTopRated()
            doReturn(emptyList<BaseModel>())
                .`when`(apiHelper)
                .getUpcomming()
            val viewModel = HomeViewModel(repository)
            viewModel.res.observeForever(apiUsersObserver)
            verify(apiHelper).getUpcomming()
            verify(apiHelper).getTopRated()
            verify(apiHelper).getPopularMovies()
            verify(apiHelper).getNowPlaying()
            verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.res.removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getNowPlaying()
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getPopularMovies()
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getTopRated()
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getUpcomming()
            val viewModel = HomeViewModel(repository)
            viewModel.res.observeForever(apiUsersObserver)
            verify(apiHelper).getUpcomming()
            verify(apiHelper).getTopRated()
            verify(apiHelper).getPopularMovies()
            verify(apiHelper).getNowPlaying()
            verify(apiUsersObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.res.removeObserver(apiUsersObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

}