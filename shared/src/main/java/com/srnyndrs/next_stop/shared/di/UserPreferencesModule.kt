package com.srnyndrs.next_stop.shared.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.srnyndrs.next_stop.shared.data.repository.UserPreferencesRepositoryImpl
import com.srnyndrs.next_stop.shared.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferencesModule {
    companion object {
        private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
            name = "com.srnyndrs.next_stop.user_preferences"
        )

        @Provides
        @Singleton
        fun provideUserDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.userDataStore
        }
    }

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        myUserPreferencesRepository: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository
}