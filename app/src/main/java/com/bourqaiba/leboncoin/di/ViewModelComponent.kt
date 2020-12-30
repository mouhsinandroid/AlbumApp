package com.bourqaiba.leboncoin.di

import com.bourqaiba.leboncoin.viewmodel.vm.AlbumViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface ViewModelComponent {
    fun injectViewAlbumApi(viewModel: AlbumViewModel)
}