package com.bourqaiba.leboncoin.data.repository

import androidx.lifecycle.LiveData
import com.bourqaiba.leboncoin.data.api.ApiServices
import com.bourqaiba.leboncoin.data.database.AlbumDatabase
import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.data.database.entity.AlbumItem
import retrofit2.Response
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val db: AlbumDatabase,
    private val apiServices: ApiServices
) : Repository {
    override suspend fun insertAlbumItem(albumItem: AlbumItem) {
        db.getAlbumDao().insertAlbumItem(albumItem)
    }

    override suspend fun getAlbum(): Response<Album> {
        return apiServices.getAlbum()
    }

    override fun getLocalAlbum(): LiveData<List<AlbumItem>>? {
       return db.getAlbumDao().getAlbum()
    }


}