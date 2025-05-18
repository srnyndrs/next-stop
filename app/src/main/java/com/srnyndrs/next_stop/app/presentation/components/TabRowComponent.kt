package com.srnyndrs.next_stop.app.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme

@Composable
fun TabRowComponent(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    onTabChange: (Int) -> Unit = {},
    contentScreens: List<@Composable () -> Unit>
) {
    // State to keep track of the selected tab index
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Column layout to arrange tabs vertically and display content screens
    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // TabRow composable to display tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = containerColor,
            contentColor = contentColor,
            indicator = { tabPositions ->
                // Indicator for the selected tab
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = indicatorColor
                )
            }
        ) {
            // Iterate through each tab title and create a tab
            tabs.forEachIndexed { index, tabTitle ->
                Tab(
                    modifier = Modifier.padding(all = 16.dp),
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        onTabChange(index)
                    }
                ) {
                    // Text displayed on the tab
                    Text(
                        text = tabTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Display the content screen corresponding to the selected tab
        contentScreens.getOrNull(selectedTabIndex)?.invoke()
    }
}

@PreviewLightDark
@Composable
fun TabRowComponentPreview() {
    NextStopTheme {
        Surface {
            TabRowComponent(
                modifier = Modifier.fillMaxWidth(),
                tabs = listOf("Tab 1", "Tab 2", "Tab 3"),
                contentScreens = listOf(
                    { Text(text = "Tab 1") },
                    { Text(text = "Tab 2") },
                    { Text(text = "Tab 3") }
                )
            )
        }
    }
}