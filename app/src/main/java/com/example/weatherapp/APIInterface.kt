package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET


interface APIInterface {

    @GET("weather?q=riyadh&units=metric&appid=1208d1ac3ff4cde691b004629f79b8c2")
    fun doGetListResources(): Call<WeatherData?>?
}