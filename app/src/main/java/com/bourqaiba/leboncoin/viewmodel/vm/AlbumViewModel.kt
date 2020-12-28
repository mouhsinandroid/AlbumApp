package com.bourqaiba.leboncoin.viewmodel.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import com.bourqaiba.leboncoin.data.repository.AlbumRepository
import com.bourqaiba.leboncoin.util.NetworkHelper
import com.bourqaiba.leboncoin.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AlbumViewModel(
    app: Application,
    private val repository: AlbumRepository
): AndroidViewModel(app) {

    private val networkHelper = NetworkHelper(app)

    val album : MutableLiveData<Resource<Album>> = MutableLiveData()

    init {
        getAlbum()
    }

    fun getAlbum() = viewModelScope.launch {
        album.postValue(Resource.loading(null))
        if (networkHelper.isNetworkConnected()) {
            album.postValue(Resource.loading(null))

            val response = repository.getAlbums()
            album.postValue(handleAlbum(response))

        } else album.postValue(Resource.error("No Wifi connection", null))
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

    fun getLocalAlbums() = repository.getLocalListAlbums()



}