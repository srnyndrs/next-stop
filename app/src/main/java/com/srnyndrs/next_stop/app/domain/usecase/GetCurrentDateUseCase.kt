package com.srnyndrs.next_stop.app.domain.usecase

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetCurrentDateUseCase @Inject constructor() {
    operator fun invoke(): String? {
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return try {
            date.format(formatter)
        } catch (e: Exception) {
            null
        }
    }
}