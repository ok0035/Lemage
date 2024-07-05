package com.zerosword.feature_main.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zerosword.domain.navigation.Routes
import com.zerosword.feature_main.ui.graph.NavigationGraph
import com.zerosword.feature_main.ui.graph.safeNavigate
import com.zerosword.resources.R.*
import com.zerosword.resources.ui.theme.DarkColorScheme
import com.zerosword.resources.ui.theme.LightColorScheme
import com.zerosword.resources.ui.theme.Typography

@Composable
fun MainScreen(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val isInEditMode = LocalInspectionMode.current

    MainTheme(
        isDarkTheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background,
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val (topBarRef, contentsRef, bottomBarRef) = createRefs()
                val topBarHeight = dimensionResource(id = dimen.top_bar_height)

                Box(
                    modifier = Modifier
                        .constrainAs(topBarRef) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                            height = Dimension.value(topBarHeight)
                        }
                        .background(colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {

                    val currentRoute = currentBackStackEntry?.destination?.route ?: Routes.Search.route
                    val searchTitle = context.getString(string.search_screen_title)
                    val bookmarkTitle = context.getString(string.bookmark_screen_title)
                    val imageViewerTitle = context.getString(string.image_viewer_title)

                    val title = when (currentRoute.split('/')[0]) {
                        Routes.Search.route -> searchTitle
                        Routes.Bookmark.route -> bookmarkTitle
                        Routes.ImageViewer.route -> imageViewerTitle
                        else -> searchTitle
                    }

                    Text(
                        modifier = Modifier,
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colorScheme.onPrimary
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .constrainAs(contentsRef) {

                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(topBarRef.bottom)
                            bottom.linkTo(bottomBarRef.top)

                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                        .background(colorScheme.background),
                ) {
                    if (!isInEditMode)
                        NavigationGraph(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                        )
                }

                Box(
                    modifier = Modifier
                        .constrainAs(bottomBarRef) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .background(colorScheme.secondary)
                ) {
                    BottomBar(navController = navController)
                }
            }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val context = LocalContext.current
    val searchTitle = context.getString(string.search_screen_title)
    val bookmarkTitle = context.getString(string.bookmark_screen_title)
    val height = dimensionResource(id = dimen.bottom_bar_height)

    val items = listOf(searchTitle, bookmarkTitle)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorScheme.secondary)
        )

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items.forEach { screen ->

                val icon: ImageVector
                val route: String
                val screenName: String

                when (screen) {
                    searchTitle -> {
                        screenName = searchTitle
                        route = Routes.Search.route
                        icon = Icons.Rounded.Search
                    }

                    bookmarkTitle -> {
                        screenName = bookmarkTitle
                        route = Routes.Bookmark.route
                        icon = Icons.Rounded.Favorite
                    }

                    else -> {
                        screenName = searchTitle
                        route = Routes.Search.route
                        icon = Icons.Rounded.Search
                    }
                }

                BottomTabItem(
                    modifier = Modifier,
                    title = screenName,
                    navController = navController,
                    destScreen = route,
                    icon = icon
                ) { navController.safeNavigate(route) }

            }
        }
    }
}

@Composable
private fun RowScope.BottomTabItem(
    modifier: Modifier = Modifier,
    title: String,
    destScreen: String,
    navController: NavHostController,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val isSelected = currentBackStackEntry?.destination?.route == destScreen

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .clickable(interactionSource = interactionSource, indication = null) {
                val currentRoute = currentBackStackEntry?.destination?.route
                if (currentRoute == destScreen) {
                    return@clickable
                }
                onClick()
            }
            .weight(1f),
    ) {

        val (iconRef, indicatorRef) = createRefs()

        Column(
            modifier = Modifier.constrainAs(iconRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterHorizontally),
                imageVector = icon,
                contentDescription = null,
                tint = when (isSelected) {
                    true -> colorScheme.tertiary
                    else -> colorScheme.onSecondary
                }
            )
            Spacer(modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if (isSelected) colorScheme.tertiary else colorScheme.onSecondary
                )
            )
        }

        if (isSelected)
            Box(
                modifier = Modifier
                    .constrainAs(indicatorRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.value(4.dp)
                    }
                    .background(colorScheme.tertiary)
            )
    }
}

@Composable
private fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}


@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    MainTheme {
        MainScreen(true)
    }
}