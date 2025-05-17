package com.srnyndrs.next_stop.app.presentation.components.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MapPinned
import com.composables.icons.lucide.MessageCircleWarning
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.sample.RoutePreviewParameterProvider
import com.srnyndrs.next_stop.app.presentation.screen_map.components.MarkerContent
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult
import com.srnyndrs.next_stop.shared.domain.model.single.Route

@Composable
fun SearchResultRow(
    modifier: Modifier = Modifier,
    searchResult: SearchResult,
    onItemClick: (SearchResult) -> Unit
) {
    Row(
        modifier = Modifier.then(modifier)
            .clickable { onItemClick(searchResult) },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when(searchResult) {
            is SearchResult.RouteResult -> {
                RouteIcon(
                    route = searchResult.route
                )
            }
            is SearchResult.StopResult -> {
                val stop = searchResult.stop
                MarkerContent(
                    selected = false,
                    colors = stop.colors,
                    rotationDegree = stop.direction ?: 0F
                )
                Text(
                    text = searchResult.stop.stopName
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun SearchResultRowPreview(@PreviewParameter(RoutePreviewParameterProvider::class) route: Route) {
    NextStopTheme {
        Surface {
            SearchResultRow(
                modifier = Modifier.fillMaxWidth().padding(6.dp),
                searchResult = SearchResult.RouteResult(
                    route = route
                ),
                onItemClick = {}
            )
        }
    }
}