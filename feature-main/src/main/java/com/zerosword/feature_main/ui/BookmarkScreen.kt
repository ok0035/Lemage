package com.zerosword.feature_main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BookmarkScreen() {
    Box(modifier = Modifier
        .background(Color.Red)
        .fillMaxSize()
    ) {

    }
}

@Composable
@Preview(showBackground = true)
fun BookmarkScreenPreview() {
    BookmarkScreen()
}