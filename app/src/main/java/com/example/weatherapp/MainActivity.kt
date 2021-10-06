package com.example.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.cardview.widget.CardView


lateinit var textViewsMap: MutableMap<String, TextView>
lateinit var refreshCard: CardView

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
            "sunset" to findViewById(R.id.sunset),
            "wind" to findViewById(R.id.wind_tv),
            "pressure" to findViewById(R.id.pressure_tv),
            "humidity" to findViewById(R.id.humidity_tv),
        )
        refreshCard = findViewById(R.id.refresh_card)
//        how to set text
//        textViewsMap["city"]!!.text = "Riyadh, SA"
        refreshCard.setOnClickListener {
            // refresh
        }
    }
}