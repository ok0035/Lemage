package com.zerosword.feature_main.ui.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.zerosword.feature_main.ui.NotificationScreen
import com.zerosword.resources.R


@Composable
fun EmptyBookmarkScreen() {
    val context = LocalContext.current
    NotificationScreen(
        mainMessage = context.getString(R.string.no_bookmark_screen_main_msg),
        subMessage = context.getString(R.string.no_bookmark_screen_sub_msg)
    )
}