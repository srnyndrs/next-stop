package com.srnyndrs.next_stop.app.presentation.screen_favourites

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.components.search.SearchBar
import com.srnyndrs.next_stop.app.presentation.components.search.SearchViewModel

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel,
    searchViewModel: SearchViewModel,
    onItemSelect: (String) -> Unit
) {

    val routes by viewModel.routeState.collectAsStateWithLifecycle()
    val searchResults by searchViewModel.results.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Favourites",
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            UiStateContainer(
                modifier = Modifier.fillMaxSize().padding(top = 72.dp),
                uiState = routes
            ) { data ->
                LazyVerticalGrid (
                    modifier = Modifier.fillMaxSize().padding(6.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    columns = GridCells.Fixed(count = 2)
                ) {
                    items(data) { route ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .requiredHeight(56.dp),
                            onClick = {
                                onItemSelect(route.routeId)
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(6.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                RouteIcon(
                                    route = route
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .padding(vertical = 3.dp, horizontal = 6.dp)
            ) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    placeholderText = "Search routes",
                    searchResults = searchResults,
                    onItemClick = { onItemSelect(it.id) }
                ) { newValue ->
                    searchViewModel.onSearchTextChange(newValue)
                }
            }
        }
    }
}