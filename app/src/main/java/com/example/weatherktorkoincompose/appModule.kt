package com.example.weatherktorkoincompose

import org.koin.dsl.module

val appModule = module {
    // Provide Repository instance
    single { Repository() }

    // Provide ViewModel instance
    single { MainViewModel(get()) }
}