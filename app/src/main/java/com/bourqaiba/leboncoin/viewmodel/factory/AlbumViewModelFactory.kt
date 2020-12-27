package com.bourqaiba.leboncoin.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bourqaiba.leboncoin.data.repository.AlbumRepository
import com.bourqaiba.leboncoin.viewmodel.vm.AlbumViewModel

class AlbumViewModelFactory(
    val app: Application,
    private val repository: AlbumRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumViewModel(app, repository) as T
    }
}