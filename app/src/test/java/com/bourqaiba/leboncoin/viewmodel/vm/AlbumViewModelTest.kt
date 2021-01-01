package com.bourqaiba.leboncoin.viewmodel.vm

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bourqaiba.leboncoin.MainCoroutineRule
import com.bourqaiba.leboncoin.data.api.ApiServices
import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import com.bourqaiba.leboncoin.di.ApiModuleTest
import com.bourqaiba.leboncoin.di.AppModule
import com.bourqaiba.leboncoin.di.DaggerViewModelComponent
import com.bourqaiba.leboncoin.repository.FakeAlbumRepository
import com.bourqaiba.leboncoin.util.NetworkHelper
import com.bourqaiba.leboncoin.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AlbumViewModelTest  {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var services: ApiServices

    @Mock
    lateinit var connect: NetworkHelper

    private val application = Mockito.mock(Application::class.java)


    @Mock
    private lateinit var apiObserver: Observer<Resource<Album>>

    private var viewModel = AlbumViewModel(application, FakeAlbumRepository(), true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder().appModule(AppModule(application))
            .apiModule(ApiModuleTest(services))
            .build()
            .injectViewAlbumApi(viewModel)
    }

    @Test
    fun getAlbumSuccess() {
        mainCoroutineRule.runBlockingTest {
            if(connect.isNetworkConnected()) {
                val albumItem = AlbumItem(
                    albumId = 10,
                    id = 2,
                    title = "Album 2",
                    url = "www.album.com",
                    thumbnailUrl = "www.ds.com"
                )
                val album = listOf(albumItem)

                doReturn(album)
                    .`when`(services)
                    .getAlbum()

                viewModel.refreshData()
                Assert.assertEquals(album, viewModel.album.value)
            }
        }
    }

    @Test
    fun getAlbumFailure() {
        mainCoroutineRule.runBlockingTest {
            if(connect.isNetworkConnected()) {

                val errorMessage = "Error Message"
                doThrow(RuntimeException(errorMessage))
                    .`when`(services)
                    .getAlbum()

                viewModel.getAlbum()
                verify(services).getAlbum()
                verify(apiObserver).onChanged(
                    Resource.error(
                        RuntimeException(errorMessage).toString(),
                        null
                    )
                )
            }
        }
    }


}