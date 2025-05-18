package com.srnyndrs.next_stop.app.presentation.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    placeholderText: String = "Search...",
    searchResults: UiState<List<SearchResult>>,
    onItemClick: (SearchResult) -> Unit,
    onTextFieldValueChange: (String) -> Unit
) {

    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var showSuggestions by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TextField for user input
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                singleLine = true,
                value = textFieldValue,
                onValueChange = { newValue ->
                    if (newValue.text != textFieldValue.text) {
                        // Change text field value
                        onTextFieldValueChange(newValue.text)

                        textFieldValue = newValue
                        // Show suggestions only when typing and input is not empty
                        showSuggestions = newValue.text.isNotEmpty()
                    }
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface, fontSize = 18.sp),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            ) { innerTextField ->
                if (textFieldValue.text.isEmpty()) {
                    Text(
                        modifier = Modifier.focusRequester(focusRequester),
                        text = placeholderText,
                        style = TextStyle(color = Color.Gray, fontSize = 18.sp)
                    )
                }
                innerTextField()
            }
        }
        Spacer(modifier = Modifier.requiredHeight(8.dp))
        AnimatedVisibility(
            visible = showSuggestions
        ) {
            UiStateContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                    .padding(6.dp),
                uiState = searchResults
            ) { data ->
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().animateContentSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items = data,
                    ) { searchResult ->
                        SearchResultRow(
                            modifier = Modifier.fillMaxWidth().padding(6.dp),
                            searchResult = searchResult,
                            onItemClick = { result ->
                                showSuggestions = false
                                onItemClick(result)
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun LocationSearchBarPreview() {
    NextStopTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                searchResults = UiState.Loading(),
                onTextFieldValueChange = {},
                onItemClick = {}
            )
        }
    }
}