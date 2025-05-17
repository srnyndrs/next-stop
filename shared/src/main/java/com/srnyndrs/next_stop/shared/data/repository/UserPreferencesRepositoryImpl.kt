package com.srnyndrs.next_stop.shared.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.srnyndrs.next_stop.shared.domain.exceptions.InvalidUserPreferenceType
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey
import com.srnyndrs.next_stop.shared.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
): UserPreferencesRepository {

    private companion object {
        val RADIUS_KEY_NAME = intPreferencesKey(name = "radius")
        val TIME_KEY_NAME = intPreferencesKey(name = "time")

        val intPreferences = mapOf(
            PreferenceKey.RADIUS to Pair(RADIUS_KEY_NAME, 550),
            PreferenceKey.TIME to Pair(TIME_KEY_NAME, 15)
        )
    }

    override suspend fun getIntPreferenceByKey(key: PreferenceKey): Result<Int> {
        return intPreferences[key]?.let { (intKey, defaultValue) ->
            Result.runCatching {
                userDataStorePreferences.data
                    .catch { exception ->
                        if (exception is IOException) {
                            emit(emptyPreferences())
                        } else {
                            throw exception
                        }
                    }
                    .map { preferences ->
                        preferences[intKey] ?: defaultValue
                    }
                    .first()
            }
        } ?: throw InvalidUserPreferenceType()
    }

    override suspend fun setIntPreferenceByKey(key: PreferenceKey, value: Int) {
        intPreferences[key]?.let { (intKey, _) ->
            Result.runCatching {
                userDataStorePreferences.edit { preferences ->
                    preferences[intKey] = value
                }
            }
        } ?: run {
            throw InvalidUserPreferenceType()
        }
    }
}