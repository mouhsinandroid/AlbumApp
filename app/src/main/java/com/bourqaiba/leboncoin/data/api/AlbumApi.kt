package com.bourqaiba.leboncoin.data.api

import com.bourqaiba.leboncoin.data.database.entity.Album
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApi {
    @GET("/img/shared/technical-test.json")
    suspend fun getAlbum(): Response<Album>
}