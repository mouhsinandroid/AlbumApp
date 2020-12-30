package com.bourqaiba.leboncoin.di

import com.bourqaiba.leboncoin.data.api.ApiServices
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun injectApi(service: ApiServices)
}