package com.example.weatherapplication.network

import com.example.weatherapplication.model.CurrentConditions
import com.example.weatherapplication.model.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") units: String,
        @Query("appId") appId: String
    ): CurrentConditions

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String,
        @Query("units") units: String,
        @Query("appId") appId: String
    ): Forecast
}