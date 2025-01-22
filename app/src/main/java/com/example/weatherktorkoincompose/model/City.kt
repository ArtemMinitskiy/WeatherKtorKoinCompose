package com.example.weatherktorkoincompose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    @SerialName("name") val name: String,
    @SerialName("country") val country: String
)