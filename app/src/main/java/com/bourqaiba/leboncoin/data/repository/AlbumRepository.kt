package com.bourqaiba.leboncoin.data.repository

import com.bourqaiba.leboncoin.data.api.RetrofitInstance
import com.bourqaiba.leboncoin.data.database.AlbumDatabase
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem

class AlbumRepository(
    private val db: AlbumDatabase
) {

    suspend fun getAlbums() = RetrofitInstance.api.getAlbums()

    suspend fun insertAlbumItem(albumItem: AlbumItem) = db.getAlbumDao()
        .insertAlbumItem(albumItem)

    fun getLocalListAlbums() = db.getAlbumDao().getAlbum()



}