package com.example.freenowtest.di

import android.content.Context
import android.location.Geocoder
import com.example.freenowtest.data.location.LocationServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoCoderModule {
    @Provides
    @Singleton
    fun provideVehicleLocation(
        geocoder: Geocoder,
    ) = LocationServiceImp(
        geocoder
    )

    @Provides
    @Singleton
    fun provideGeocoder(
        @ApplicationContext context: Context,
    ) = Geocoder(context, Locale.getDefault())
}