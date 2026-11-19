package com.example.savour.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.savour.ui.detail.DetailScreen
import com.example.savour.ui.favorites.FavoritesScreen
import com.example.savour.ui.list.SearchScreen

private object Routes {
    const val SEARCH = "search"
    const val FAVORITES = "favorites"
    const val DETAIL = "detail/{code}"
    fun detail(code: String) = "detail/$code"
}

private data class TopLevelDestination(val route: String, val label: String, val icon: ImageVector)

private val topLevelDestinations = listOf(
    TopLevelDestination(Routes.SEARCH, "Search", Icons.Default.Search),
    TopLevelDestination(Routes.FAVORITES, "Favorites", Icons.Default.Favorite)
)

@Composable
fun SavourNavGraph(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Only show the bottom bar on the two main tabs, not on the detail screen
            if (topLevelDestinations.any { it.route == currentRoute }) {
                NavigationBar {
                    topLevelDestinations.forEach { dest ->
                        NavigationBarItem(
                            selected = currentRoute == dest.route,
                            onClick = {
                                navController.navigate(dest.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(dest.icon, contentDescription = null) },
                            label = { Text(dest.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SEARCH,
            // consumeWindowInsets stops the detail screen's own Scaffold from adding the same padding again
            modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding)
        ) {
            composable(Routes.SEARCH) {
                SearchScreen(onFoodClick = { code -> navController.navigate(Routes.detail(code)) })
            }
            composable(Routes.FAVORITES) {
                FavoritesScreen(onFoodClick = { code -> navController.navigate(Routes.detail(code)) })
            }
            composable(
                Routes.DETAIL,
                arguments = listOf(navArgument("code") { type = NavType.StringType })
            ) { entry ->
                DetailScreen(
                    code = entry.arguments?.getString("code").orEmpty(),
                    onBack = { navController.navigateUp() }
                )
            }
        }
    }
}
