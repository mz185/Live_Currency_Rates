package com.example.live_currency_rates.data

import com.example.live_currency_rates.data.model.Rates
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Marinos Zinonos on 04/03/2022.
 */

interface API {
    @GET("/rates")
    suspend fun getRates() : Rates

    companion object {
        fun createEndpoint() : API =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pricing-staging.unleashedcapital.com")
                .build()
                .create(API::class.java)
    }
}