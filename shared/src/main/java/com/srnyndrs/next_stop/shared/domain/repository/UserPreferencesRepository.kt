package com.srnyndrs.next_stop.shared.domain.repository

import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey

interface UserPreferencesRepository {
    suspend fun getIntPreferenceByKey(key: PreferenceKey): Result<Int>
    suspend fun setIntPreferenceByKey(key: PreferenceKey, value: Int)
}