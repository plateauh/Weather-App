package com.example.weatherapp

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("weather")
    val weather: List<Weather>? = null,
    @SerializedName("wind")
    val wind: Wind? = null
)

data class Main(
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("temp")
    val temp: Double? = null,
    @SerializedName("temp_max")
    val temp_max: Double? = null,
    @SerializedName("temp_main")
    val temp_min: Double? = null
)

data class Sys(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("sunrise")
    val sunrise: Int? = null,
    @SerializedName("sunset")
    val sunset: Int? = null,
)

data class Weather(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("main")
    val main: String? = null
)

data class Wind(
    @SerializedName("deg")
    val deg: Int? = null,
    @SerializedName("gust")
    val gust: Double? = null,
    @SerializedName("speed")
    val speed: Double? = null
)