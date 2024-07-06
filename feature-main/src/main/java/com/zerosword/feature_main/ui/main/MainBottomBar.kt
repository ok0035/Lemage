package com.zerosword.feature_main.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zerosword.domain.navigation.Routes
import com.zerosword.feature_main.ui.graph.safeNavigate
import com.zerosword.resources.R

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val searchTitle = context.getString(R.string.search_screen_title)
    val bookmarkTitle = context.getString(R.string.bookmark_screen_title)
    val height = dimensionResource(id = R.dimen.bottom_bar_height)

    val items = listOf(searchTitle, bookmarkTitle)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.secondary)
        )

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items.forEach { screen ->
                val icon: ImageVector
                val route: String

                when (screen) {
                    searchTitle -> {
                        route = Routes.Search.route
                        icon = Icons.Rounded.Search
                    }
                    bookmarkTitle -> {
                        route = Routes.Bookmark.route
                        icon = Icons.Rounded.Favorite
                    }
                    else -> {
                        route = Routes.Search.route
                        icon = Icons.Rounded.Search
                    }
                }

                BottomTabItem(
                    title = screen,
                    navController = navController,
                    destScreen = route,
                    icon = icon
                ) { navController.safeNavigate(route) }
            }
        }
    }
}
