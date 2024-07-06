package com.zerosword.feature_main.ui.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
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

@Composable
fun MainScreen(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val isInEditMode = LocalInspectionMode.current

    MainTheme(isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background,
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (topBarRef, contentsRef, bottomBarRef) = createRefs()
                val topBarHeight = dimensionResource(id = dimen.top_bar_height)

                MainTopBar(
                    modifier = Modifier.constrainAs(topBarRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.value(topBarHeight)
                    },
                    currentRoute = currentBackStackEntry?.destination?.route ?: Routes.Search.route
                )

                Box(
                    modifier = Modifier.constrainAs(contentsRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(topBarRef.bottom)
                        bottom.linkTo(bottomBarRef.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                        .background(colorScheme.background),
                ) {
                    if (!isInEditMode) {
                        NavigationGraph(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                        )
                    }
                }

                BottomBar(
                    modifier = Modifier.constrainAs(bottomBarRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    MainTheme {
        MainScreen(true)
    }
}