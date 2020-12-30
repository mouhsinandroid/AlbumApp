package com.bourqaiba.leboncoin.data.api

import com.bourqaiba.leboncoin.data.database.entity.Album
import com.bourqaiba.leboncoin.di.DaggerApiComponent
import retrofit2.Response
import javax.inject.Inject

class ApiServices {

    @Inject
    lateinit var api: AlbumApi

    init {
        DaggerApiComponent.create().injectApi(this)
    }

    suspend fun getAlbum(): Response<Album> {
        return api.getAlbum()
    }
}