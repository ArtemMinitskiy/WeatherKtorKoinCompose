package com.example.weatherktorkoincompose

import android.os.Environment
import android.util.Log
import com.example.weatherktorkoincompose.Constants.API_KEY
import com.example.weatherktorkoincompose.Constants.BASE_URL
import com.example.weatherktorkoincompose.Constants.COUNTRY_CODE
import com.example.weatherktorkoincompose.model.ForecastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

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

    suspend fun downloadFile(url: String) {
        Log.e("mLog", "downloadFile: START")
        var progress: Int
        val client = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 60_000
            }
        }

        try {
            val downloadsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, url.substringAfterLast("/"))

            val response = client.get(url)
            val contentLength = response.headers["Content-Length"]?.toLong() ?: -1L

            val channel: ByteReadChannel = response.body()
            val outputStream =
                withContext(Dispatchers.IO) {
                    FileOutputStream(file)
                }

            val buffer = ByteArray(1024 * 8)
            var totalBytesRead = 0L
            var bytesRead: Int

            while (channel.readAvailable(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
                totalBytesRead += bytesRead

                // Update progress
                if (contentLength > 0) {
                    progress = (totalBytesRead / contentLength.toFloat()).roundToInt()
                    Log.e("mLog", "Progress: $progress")
                }
            }

        } catch (e: Exception) {
            Log.e("mLog", "Error downloading file", e)
        }
    }
}