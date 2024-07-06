package com.zerosword.feature_main.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.zerosword.domain.navigation.Routes
import com.zerosword.resources.R

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
) {
    val context = LocalContext.current

    val searchTitle = context.getString(R.string.search_screen_title)
    val bookmarkTitle = context.getString(R.string.bookmark_screen_title)
    val imageViewerTitle = context.getString(R.string.image_viewer_title)

    val title = when (currentRoute.split('/')[0]) {
        Routes.Search.route -> searchTitle
        Routes.Bookmark.route -> bookmarkTitle
        Routes.ImageViewer.route -> imageViewerTitle
        else -> searchTitle
    }

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}