package com.srnyndrs.next_stop.app.domain.usecase

import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class SearchByQueryUseCase @Inject constructor(
    private val transportRepository: TransportRepository
) {
    suspend operator fun invoke(query: String): Result<List<SearchResult>> {
        return transportRepository.searchByQuery(query)
    }
}