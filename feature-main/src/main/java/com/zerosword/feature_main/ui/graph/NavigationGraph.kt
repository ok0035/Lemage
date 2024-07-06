package com.zerosword.feature_main.ui.graph

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zerosword.domain.navigation.NavArgs
import com.zerosword.domain.navigation.Routes
import com.zerosword.feature_main.ui.bookmark.BookmarkScreen
import com.zerosword.feature_main.ui.FullScreenImageScreen
import com.zerosword.feature_main.ui.search.SearchScreen
import com.zerosword.feature_main.viewmodel.BookmarkViewModel
import com.zerosword.feature_main.viewmodel.SearchViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    //탭을 이동한 뒤에도 결과가 남아있도록 하기 위해 LocalContext를 Owner로 설정
    val searchViewModel: SearchViewModel = hiltViewModel()
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.Search.route,
    ) {

        composable(route = Routes.Search.route) {
            SearchScreen(searchViewModel, navController)
        }
        composable(route = Routes.Bookmark.route) {
            BookmarkScreen(
                bookmarkViewModel,
                navController
            )
        }
        composable(route = "${Routes.ImageViewer.route}/{${NavArgs.ImageUrl.arg}}",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) { backStackEntry ->
            val imageUrl = backStackEntry.arguments?.getString(NavArgs.ImageUrl.arg)
            imageUrl?.let { url ->
                FullScreenImageScreen(encodedImageUrl = url)
            }
        }
    }
}

fun NavController.safeNavigate(route: String) {
    val backStackEntry = currentBackStackEntry
    if (backStackEntry?.destination?.route != route)
        navigate(route)
}