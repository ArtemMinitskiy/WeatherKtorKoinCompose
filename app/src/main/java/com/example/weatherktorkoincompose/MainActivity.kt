package com.example.weatherktorkoincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherktorkoincompose.ui.theme.Background
import com.example.weatherktorkoincompose.ui.theme.WeatherKtorKoinComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val scrollState = rememberLazyListState()

            WeatherKtorKoinComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background)
                ) { innerPadding ->
                    val state = viewModel.forecast.collectAsState()

                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        state.value.city?.name?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        state.value.forecasts?.let { forecasts ->
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                state = scrollState
                            ) {
                                itemsIndexed(items = forecasts, itemContent = { index, forecast ->
                                    ForecastItem(forecast)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getAnimation(icon: String): Int {
    return when (icon) {
        "01d" -> R.raw.weather_day_clear_sky
        "02d" -> R.raw.weather_day_few_clouds
        "03d" -> R.raw.weather_day_scattered_clouds
        "04d" -> R.raw.weather_day_broken_clouds
        "09d" -> R.raw.weather_day_shower_rains
        "10d" -> R.raw.weather_day_rain
        "11d" -> R.raw.weather_day_thunderstorm
        "13d" -> R.raw.weather_day_snow
        "50d" -> R.raw.weather_day_mist
        "01n" -> R.raw.weather_night_clear_sky
        "02n" -> R.raw.weather_night_few_clouds
        "03n" -> R.raw.weather_night_scattered_clouds
        "04n" -> R.raw.weather_night_broken_clouds
        "09n" -> R.raw.weather_night_shower_rains
        "10n" -> R.raw.weather_night_rain
        "11n" -> R.raw.weather_night_thunderstorm
        "13n" -> R.raw.weather_night_snow
        "50n" -> R.raw.weather_night_mist
        else -> 0
    }
}