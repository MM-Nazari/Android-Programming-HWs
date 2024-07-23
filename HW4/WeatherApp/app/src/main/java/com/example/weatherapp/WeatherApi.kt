package com.example.weatherapp

import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    @GET("/data/2.5/forecast?lat=32.427910&lon=53.688046&appid=fa1be7c55be9f3b7522698134708ecb2")
    suspend fun getWeather(): Response<List<Weather>>

}