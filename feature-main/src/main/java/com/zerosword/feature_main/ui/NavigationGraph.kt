package com.zerosword.feature_main.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zerosword.feature_main.R

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val searchTitle = context.getString(com.zerosword.resources.R.string.search_screen_title)
    val bookmarkTitle = context.getString(com.zerosword.resources.R.string.bookmark_screen_title)

    NavHost(modifier = modifier, navController = navController, startDestination = searchTitle) {
        composable(searchTitle) { SearchScreen() }
        composable(bookmarkTitle) { BookmarkScreen() }
    }
}