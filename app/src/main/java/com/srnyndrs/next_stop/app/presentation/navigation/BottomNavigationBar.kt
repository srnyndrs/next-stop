package com.srnyndrs.next_stop.app.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onItemSelect: (String) -> Unit
) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf(
        Screen.HomeScreen,
        Screen.MapScreen,
        Screen.FavouritesScreen,
        Screen.SettingsScreen
    )

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            selectedItemIndex = when (backStackEntry.destination.route) {
                Screen.HomeScreen.route -> 0
                Screen.MapScreen.route -> 1
                Screen.FavouritesScreen.route -> 2
                Screen.SettingsScreen.route -> 3
                else -> selectedItemIndex
            }
        }
    }

    NavigationBar(
        modifier = Modifier.then(modifier),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        tonalElevation = 12.dp
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    onItemSelect(screen.route)
                },
                icon = {
                    screen.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = null
                        )
                    }
                },
                label = {
                    Text(text = screen.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}