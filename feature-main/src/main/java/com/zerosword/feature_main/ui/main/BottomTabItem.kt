package com.zerosword.feature_main.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun RowScope.BottomTabItem(
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
                    true -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.onSecondary
                }
            )
            Spacer(modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSecondary
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
                    .background(MaterialTheme.colorScheme.tertiary)
            )
    }
}
