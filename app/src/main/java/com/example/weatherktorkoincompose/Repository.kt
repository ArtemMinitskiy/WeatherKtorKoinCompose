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
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class Repository {
    fun fetchFiveDayForecast(): Flow<ResultState<ForecastResponse>> = flow {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        emit(ResultState.Loading)
        try {
            val response: HttpResponse = client.get(BASE_URL)
            {
                parameter("q", COUNTRY_CODE)
                parameter("appid", API_KEY)
                parameter("units", "metric")
            }
            if (response.status == HttpStatusCode.OK) {
                emit(ResultState.Success(response.body()))
            } else {
                emit(ResultState.Error("HTTP Error: ${response.status}"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error"))
        }
    }
}