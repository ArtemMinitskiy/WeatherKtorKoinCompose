package com.example.weatherktorkoincompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherktorkoincompose.model.ForecastResponse
import com.example.weatherktorkoincompose.ui.theme.Background
import com.example.weatherktorkoincompose.ui.theme.WeatherKtorKoinComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = Repository()
        val viewModel = MainViewModel(repository)
        viewModel.loadData()

        setContent {
            val scrollState = rememberLazyListState()
            val uiState by viewModel.forecast.collectAsState()

            WeatherKtorKoinComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background)
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        when (uiState) {
                            is ResultState.Loading -> {
                                Log.i("mLog", "Loading")
                                CircularProgressIndicator()
                            }

                            is ResultState.Success -> {
                                Log.i("mLog", "Success ${(uiState as ResultState.Success<ForecastResponse>).data}")
                                val forecastResponse =
                                    (uiState as ResultState.Success<ForecastResponse>).data

                                forecastResponse.city?.name?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                forecastResponse.forecasts?.let { forecasts ->
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        state = scrollState
                                    ) {
                                        itemsIndexed(
                                            items = forecasts,
                                            itemContent = { index, forecast ->
                                                ForecastItem(forecast)
                                            })
                                    }
                                }
                            }

                            is ResultState.Error -> Log.e("mLog", "Error ${(uiState as ResultState.Error).message}")
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