package com.srnyndrs.next_stop.shared.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.srnyndrs.next_stop.shared.data.service.LocationServiceImpl
import com.srnyndrs.next_stop.shared.domain.service.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideLocationService(
        @ApplicationContext context: Context
    ): LocationService {
        return LocationServiceImpl(
            context,
            LocationServices.getFusedLocationProviderClient(context)
        )
    }
}