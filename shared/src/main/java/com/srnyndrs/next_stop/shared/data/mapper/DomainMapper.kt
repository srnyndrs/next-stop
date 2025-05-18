package com.srnyndrs.next_stop.shared.data.mapper

import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.Stop

fun Stop.toSearchResult(): SearchResult {
    return SearchResult.StopResult(this)
}

fun Route.toSearchResult(): SearchResult {
    return SearchResult.RouteResult(this)
}