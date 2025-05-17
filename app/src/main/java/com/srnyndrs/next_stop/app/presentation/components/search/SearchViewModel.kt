package com.srnyndrs.next_stop.app.presentation.components.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.next_stop.app.domain.usecase.SearchByQueryUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@HiltViewModel(assistedFactory = SearchViewModel.SearchViewModelFactory::class)
class SearchViewModel @AssistedInject constructor(
    @Assisted val resultType: KClass<out SearchResult>,
    private val searchByQueryUseCase: SearchByQueryUseCase
): ViewModel() {

    @AssistedFactory
    interface SearchViewModelFactory {
        fun create(resultType: KClass<out SearchResult>): SearchViewModel
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _results = MutableStateFlow<UiState<List<SearchResult>>>(UiState.Empty())
    @OptIn(FlowPreview::class)
    val results = searchText
        .debounce(2000L)
        .onEach { query ->
            val filter = { a: SearchResult -> resultType.java.isInstance(a) }
            if(query.isNotBlank()) {
                searchByQuery(query, filter)
            }
        }
        .combine(_results) { _, results ->
            results
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000),
            _results.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private fun searchByQuery(query: String, resultFilter: (SearchResult) -> Boolean) = viewModelScope.launch {
        _results.value = UiState.Loading()
        searchByQueryUseCase(query).fold(
            onSuccess = { list ->
                _results.value = UiState.Success(data = list.filter(resultFilter))
            },
            onFailure = {
                _results.value = UiState.Error(message = it.message ?: "An error occurred")
            }
        )
    }
}