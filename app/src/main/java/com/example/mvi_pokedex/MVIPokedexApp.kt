package com.example.mvi_pokedex

import android.app.Application
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
@Module
@InstallIn(SingletonComponent::class)
class MVIPokedexApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}

