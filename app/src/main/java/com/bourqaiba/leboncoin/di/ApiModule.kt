package com.bourqaiba.leboncoin.di

import com.bourqaiba.leboncoin.BuildConfig
import com.bourqaiba.leboncoin.data.api.AlbumApi
import com.bourqaiba.leboncoin.data.api.ApiServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
open class ApiModule {

    @Provides
    fun provideAlbumApi(): AlbumApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return  Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(AlbumApi::class.java)
    }

    @Provides
    open fun provideApiService(): ApiServices {
        return ApiServices()
    }
}