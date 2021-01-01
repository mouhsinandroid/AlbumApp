package com.bourqaiba.leboncoin.viewmodel.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bourqaiba.leboncoin.R
import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import com.bourqaiba.leboncoin.data.repository.Repository
import com.bourqaiba.leboncoin.di.AppModule
import com.bourqaiba.leboncoin.di.DaggerViewModelComponent
import com.bourqaiba.leboncoin.util.NetworkHelper
import com.bourqaiba.leboncoin.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AlbumViewModel(
    app: Application,
    private val repository: Repository
): AndroidViewModel(app) {

    constructor(application: Application, repository: Repository, test: Boolean= true): this(application, repository) {
        injected = true
    }

    private var injected = false
    private val networkHelper = NetworkHelper(app)

    val album by lazy { MutableLiveData<Resource<Album>>() }


    private fun inject() {
        if(!injected) {
            DaggerViewModelComponent.builder().appModule(AppModule(getApplication()))
                    .build().injectViewAlbumApi(this)
        }
    }

    fun refreshData() {
        inject()
        getAlbum()
    }

    fun getAlbum() = viewModelScope.launch {
        album.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            album.postValue(Resource.loading(null))

            val response = repository.getAlbum()
            album.postValue(handleAlbum(response))

        } else album.postValue(Resource.error(noWifiMessage(), null))
    }

    private fun handleAlbum(response: Response<Album>): Resource<Album> {
        if(response.isSuccessful) {
            response.body()?.let {result ->
                return Resource.success(result)
            }
        }
        return Resource.error(response.message(), null)
    }


    fun saveAlbumItem(albumItem: AlbumItem) = viewModelScope.launch {
        repository.insertAlbumItem(albumItem)
    }

    fun getAlbumFromLocal() = repository.getLocalAlbum()

    private fun noWifiMessage(): String {
        return getApplication<Application>().resources.getString(R.string.no_wifi)
    }



}