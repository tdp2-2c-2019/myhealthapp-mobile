package com.example.admin.di

import com.example.admin.repositories.ATMRepository
import com.example.admin.screens.map.MapsActivity
import com.example.admin.screens.map.MapViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MapsActivityModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun providesMapViewModelFactory(weatherRepository: ATMRepository) : MapViewModelFactory {
            return MapViewModelFactory(weatherRepository)
        }
    }

    @ContributesAndroidInjector
    internal abstract fun mapsActivity(): MapsActivity

}