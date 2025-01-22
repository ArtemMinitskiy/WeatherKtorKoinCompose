package com.example.weatherktorkoincompose

import com.example.weatherktorkoincompose.Constants.API_KEY
import com.example.weatherktorkoincompose.Constants.BASE_URL
import com.example.weatherktorkoincompose.Constants.COUNTRY_CODE
import com.example.weatherktorkoincompose.model.ForecastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorApiClient {
    suspend fun fetchFiveDayForecast(): ForecastResponse {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        return client.get(BASE_URL) {
            parameter("q", COUNTRY_CODE)
            parameter("appid", API_KEY)
            parameter("units", "metric")
        }.body()
    }
}