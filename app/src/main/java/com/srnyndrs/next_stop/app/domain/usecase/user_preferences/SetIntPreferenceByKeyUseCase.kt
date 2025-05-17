package com.srnyndrs.next_stop.app.domain.usecase.user_preferences

import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey
import com.srnyndrs.next_stop.shared.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SetIntPreferenceByKeyUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(key: PreferenceKey, value: Int): Boolean {
        return try {
            userPreferencesRepository.setIntPreferenceByKey(key, value)
            true
        } catch (e: Exception) {
            false
        }
    }
}