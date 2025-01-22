package com.example.weatherktorkoincompose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastEntry(
    @SerialName("dt") val dateTime: Long,
    @SerialName("main") val main: MainInfo,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("dt_txt") val dateText: String
)
