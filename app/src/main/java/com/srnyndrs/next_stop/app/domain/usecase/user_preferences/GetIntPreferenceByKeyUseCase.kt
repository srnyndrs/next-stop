package com.srnyndrs.next_stop.app.domain.usecase.user_preferences

import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey
import com.srnyndrs.next_stop.shared.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class GetIntPreferenceByKeyUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(key: PreferenceKey): Int {
        return userPreferencesRepository.getIntPreferenceByKey(key).fold(
            onSuccess = {
                it
            },
            onFailure = {
                throw IllegalStateException("Failed to get preference value", it)
            }
        )
    }
}