package com.example.weatherktorkoincompose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("city") val city: City? = null,
    @SerialName("list") val forecasts: List<ForecastEntry>? = null
)
