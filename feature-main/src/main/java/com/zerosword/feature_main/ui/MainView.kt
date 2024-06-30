package com.zerosword.feature_main.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainView(
    darkTheme: Boolean = isSystemInDarkTheme(),
) {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    MainTheme(
        darkTheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background,
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val (topBarRef, contentsRef, bottomBarRef) = createRefs()
                val topBarHeight = dimensionResource(
                    id = com.zerosword.resources.R.dimen.top_bar_height
                )

                Box(
                    modifier = Modifier.constrainAs(topBarRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)

                        height = Dimension.value(topBarHeight)
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = currentBackStackEntry?.destination?.route ?: "")
                }

                Box(
                    modifier = Modifier.constrainAs(contentsRef) {

                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(topBarRef.bottom)
                        bottom.linkTo(bottomBarRef.top)

                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                ) {
                    NavigationGraph(navController = navController)
                }

                Box(
                    modifier = Modifier.constrainAs(bottomBarRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    BottomBar(navController = navController)
                }

            }
        }

    }
}

@Composable
fun TopBar(title: String) {

}

@Composable
fun BottomBar(navController: NavHostController) {
    val context = LocalContext.current
    val searchTitle = context.getString(com.zerosword.resources.R.string.search_screen_title)
    val bookmarkTitle = context.getString(com.zerosword.resources.R.string.bookmark_screen_title)
    val height = dimensionResource(id = com.zerosword.resources.R.dimen.bottom_bar_height)

    val items = listOf(searchTitle, bookmarkTitle)

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        items.forEach { screen ->

            val icon = when (screen) {
                searchTitle -> Icons.Rounded.Search
                bookmarkTitle -> Icons.Rounded.Favorite
                else -> Icons.Rounded.Search
            }

            BottomTabItem(
                navController = navController,
                destScreen = screen,
                icon = icon
            ) { navController.navigate(screen) }

        }
    }
}

@Composable
fun RowScope.BottomTabItem(
    modifier: Modifier = Modifier,
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
                tint = when(isSelected) {
                    true -> colorScheme.secondary
                    else -> colorScheme.tertiary
                }
            )
            Spacer(modifier.height(2.dp))
            Text(
                text = destScreen,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if (isSelected) colorScheme.secondary else colorScheme.tertiary
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
                    .background(colorScheme.secondary)
            )
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background,
        ) {
            MainView()
        }
    }
}