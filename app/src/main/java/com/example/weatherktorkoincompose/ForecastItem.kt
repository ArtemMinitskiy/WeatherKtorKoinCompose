package com.example.weatherktorkoincompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherktorkoincompose.model.ForecastEntry
import com.example.weatherktorkoincompose.ui.theme.CardBackground

@Composable
fun ForecastItem(forecast: ForecastEntry) {
    val animationRes = remember { mutableStateOf(getAnimation(forecast.weather[0].icon)) }
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes.value))

    Box(
        Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(120.dp)
            .background(CardBackground, RoundedCornerShape(20.dp))
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .align(Alignment.TopStart)
        ) {
            Text(
                text = forecast.main.temperature.toString() + " \u00B0C",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                color = Color.White
            )
            Text(
                text = forecast.weather[0].description,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp),
                color = Color.White
            )
        }
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(80.dp),
            composition = composition.value,
            iterations = LottieConstants.IterateForever,
        )
    }
}