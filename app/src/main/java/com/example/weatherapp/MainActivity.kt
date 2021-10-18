package com.example.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


lateinit var textViewsMap: MutableMap<String, TextView>
lateinit var refreshCard: CardView
lateinit var weatherData: WeatherData

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewsMap = mutableMapOf(
            "city" to findViewById(R.id.city_tv),
            "date and time" to findViewById(R.id.date_time_tv),
            "status" to findViewById(R.id.status_tv),
            "temperature" to findViewById(R.id.temperature_tv),
            "low" to findViewById(R.id.low_tv),
            "high" to findViewById(R.id.high_tv),
            "sunrise" to findViewById(R.id.sunrise_tv),
            "sunset" to findViewById(R.id.sunset_tv),
            "wind" to findViewById(R.id.wind_tv),
            "pressure" to findViewById(R.id.pressure_tv),
            "humidity" to findViewById(R.id.humidity_tv)
        )

        CoroutineScope(IO).launch {
            setWeather()
        }

        refreshCard = findViewById(R.id.refresh_card)
        refreshCard.setOnClickListener {
            CoroutineScope(IO).launch {
                setWeather()
            }
        }
    }

    private suspend fun setWeather() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<WeatherData?>? = apiInterface!!.doGetListResources()
        try {
            weatherData = call?.execute()?.body()!!
            setData()
        } catch (e: Exception){ e.printStackTrace() }
    }

    private suspend fun setData() {

            withContext(Main) {
                textViewsMap["city"]!!.text = "${weatherData?.name}, ${weatherData?.sys?.country}"
                textViewsMap["date and time"]!!.text = "Updated at: ${weatherData?.dt}"
                textViewsMap["status"]!!.text = weatherData?.weather?.get(0)?.description.toString()
                textViewsMap["temperature"]!!.text = "${weatherData?.main?.temp?.toInt()}°C"
                textViewsMap["low"]!!.text = "Low: ${weatherData?.main?.temp_min?.toInt()}°C"
                textViewsMap["high"]!!.text = "High: ${weatherData?.main?.temp_max?.toInt()}°C"
                textViewsMap["sunrise"]!!.text = weatherData?.sys?.sunrise.toString()
                textViewsMap["sunset"]!!.text = weatherData?.sys?.sunset.toString()
                textViewsMap["wind"]!!.text = weatherData?.wind?.speed?.toString()
                textViewsMap["pressure"]!!.text = weatherData?.main?.pressure.toString()
                textViewsMap["humidity"]!!.text = weatherData?.main?.humidity.toString()
            }
    }
}

