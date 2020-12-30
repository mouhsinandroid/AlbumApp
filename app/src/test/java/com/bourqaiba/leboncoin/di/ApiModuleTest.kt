package com.bourqaiba.leboncoin.di


import com.bourqaiba.leboncoin.data.api.ApiServices

class ApiModuleTest(private val mockServices: ApiServices): ApiModule() {

    override fun provideApiService(): ApiServices {
        return mockServices
    }

}