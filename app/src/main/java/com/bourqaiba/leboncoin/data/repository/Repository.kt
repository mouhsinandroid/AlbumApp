package com.bourqaiba.leboncoin.data.repository

import androidx.lifecycle.LiveData
import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import retrofit2.Response

interface Repository {
    suspend fun insertAlbumItem(albumItem: AlbumItem)
    suspend fun getAlbum(): Response<Album>
    fun getLocalAlbum() : LiveData<List<AlbumItem>>?
}