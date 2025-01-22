package com.example.weatherktorkoincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherktorkoincompose.model.ForecastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {
        getForecast()
    }

    private val _forecast = MutableStateFlow(ForecastResponse())
    var forecast: StateFlow<ForecastResponse> = _forecast

    private fun getForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            _forecast.emit(KtorApiClient().fetchFiveDayForecast())
        }
    }
}