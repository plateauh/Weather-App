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
import java.util.*


lateinit var textViewsMap: MutableMap<String, TextView>
lateinit var refreshCard: CardView
var weatherData: WeatherData? = WeatherData()

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
        setData(textViewsMap)
        refreshCard = findViewById(R.id.refresh_card)
        refreshCard.setOnClickListener {
             setData(textViewsMap)
        }
    }

    private fun setWeather() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<WeatherData?>? = apiInterface!!.doGetListResources()
        call?.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: Call<WeatherData?>?, response: Response<WeatherData?>) {
                weatherData = response.body()
            }
            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private fun setData(textViewsMap: MutableMap<String, TextView>){
        CoroutineScope(IO).launch {
            setWeather()
            withContext(Main) {
                val offset = TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings
                textViewsMap["city"]!!.text = "${weatherData?.name}, ${weatherData?.sys?.country}"
                textViewsMap["date and time"]!!.text = "Updated at: ${weatherData?.dt?.minus(offset)}"
                textViewsMap["status"]!!.text = "${weatherData?.weather?.get(0)?.description}"
                textViewsMap["temperature"]!!.text = "${weatherData?.main?.temp?.toInt()}°C"
                textViewsMap["low"]!!.text = "Low: ${weatherData?.main?.temp_min?.toInt()}°C"
                textViewsMap["high"]!!.text = "High: ${weatherData?.main?.temp_max?.toInt()}°C"
                textViewsMap["sunrise"]!!.text = "${weatherData?.sys?.sunrise?.minus(offset)}"
                textViewsMap["sunset"]!!.text = "${weatherData?.sys?.sunset?.minus(offset)}"
                textViewsMap["wind"]!!.text = "${weatherData?.wind?.speed}"
                textViewsMap["pressure"]!!.text = "${weatherData?.main?.pressure}"
                textViewsMap["humidity"]!!.text = "${weatherData?.main?.humidity}"
            }
        }
    }


}

