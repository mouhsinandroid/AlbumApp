package com.bourqaiba.leboncoin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import com.bourqaiba.leboncoin.data.repository.Repository
import retrofit2.Response

class FakeAlbumRepository : Repository{


    private val album = mutableListOf<AlbumItem>()

    private val observableAlbumItems = MutableLiveData<List<AlbumItem>>(album)

    fun refreshAlbum() {
        observableAlbumItems.postValue(album)
    }

    override suspend fun insertAlbumItem(albumItem: AlbumItem) {
        album.add(albumItem)
    }

    override suspend fun getAlbum(): Response<Album> {
        return Response.success(Album())
    }

    override fun getLocalAlbum(): LiveData<List<AlbumItem>> {
        return MutableLiveData(album)
    }
}