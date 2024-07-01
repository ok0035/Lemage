package com.zerosword.feature_main.ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zerosword.feature_main.ui.BookmarkScreen
import com.zerosword.feature_main.ui.SearchScreen
import com.zerosword.feature_main.viewmodel.BookmarkViewModel
import com.zerosword.feature_main.viewmodel.SearchViewModel
import com.zerosword.resources.R.*

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val context = LocalContext.current
    val searchRoute = context.getString(string.search_screen_title)
    val bookmarkRoute = context.getString(string.bookmark_screen_title)

    val searchViewModel: SearchViewModel = hiltViewModel()
    val bookmarkViewModel: BookmarkViewModel =  hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = searchRoute
    ) {
        composable(searchRoute) { SearchScreen(searchViewModel) }
        composable(bookmarkRoute) { BookmarkScreen(bookmarkViewModel) }
    }
}