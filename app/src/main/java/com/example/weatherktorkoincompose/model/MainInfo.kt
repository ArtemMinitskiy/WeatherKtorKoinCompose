package com.example.weatherktorkoincompose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainInfo(
    @SerialName("temp") val temperature: Double,
    @SerialName("temp_min") val minTemp: Double,
    @SerialName("temp_max") val maxTemp: Double
)
