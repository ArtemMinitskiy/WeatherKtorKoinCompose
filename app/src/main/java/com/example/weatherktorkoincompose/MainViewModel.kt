package com.example.weatherktorkoincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherktorkoincompose.Constants.DOWNLOAD_LINK
import com.example.weatherktorkoincompose.model.ForecastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _forecast = MutableStateFlow<ResultState<ForecastResponse>>(ResultState.Loading)
    val forecast: StateFlow<ResultState<ForecastResponse>> get() = _forecast

    private fun loadData() {
        viewModelScope.launch {
            repository.fetchFiveDayForecast().collect { result ->
                _forecast.value = result
            }
        }
    }

    private fun download(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.downloadFile(url)
        }
    }

    init {
        loadData()
        download(DOWNLOAD_LINK)
    }
}