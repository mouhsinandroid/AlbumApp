package com.bourqaiba.leboncoin.viewmodel.vm

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bourqaiba.leboncoin.MainCoroutineRule
import com.bourqaiba.leboncoin.data.api.ApiServices
import com.bourqaiba.leboncoin.di.ApiModuleTest
import com.bourqaiba.leboncoin.di.AppModule
import com.bourqaiba.leboncoin.di.DaggerViewModelComponent
import com.bourqaiba.leboncoin.repository.FakeAlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AlbumViewModelTest  {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var services: ApiServices

    private val application = Mockito.mock(Application::class.java)



    private var viewModel = AlbumViewModel(application, FakeAlbumRepository())

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder().appModule(AppModule(application))
            .apiModule(ApiModuleTest(services))
            .build()
            .injectViewAlbumApi(viewModel)
    }


}