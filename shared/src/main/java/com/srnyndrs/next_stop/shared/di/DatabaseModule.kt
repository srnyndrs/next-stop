package com.srnyndrs.next_stop.shared.di

import android.content.Context
import androidx.room.Room
import com.srnyndrs.next_stop.shared.data.local.dao.RouteDao
import com.srnyndrs.next_stop.shared.data.local.database.AppDatabase
import com.srnyndrs.next_stop.shared.data.repository.RouteRepositoryImpl
import com.srnyndrs.next_stop.shared.domain.repository.RouteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigrationFrom()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideRouteDao(database: AppDatabase) = database.routeDao()

    @Singleton
    @Provides
    fun provideRouteRepository(routeDao: RouteDao): RouteRepository {
        return RouteRepositoryImpl(routeDao)
    }
}