package com.zerosword.feature_main.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zerosword.resources.R

@Composable
fun BookmarkHeader(keyword: String) {

    val headerHeight = dimensionResource(id = R.dimen.bookmark_header_height)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = keyword,
            style = MaterialTheme
                .typography
                .headlineSmall
                .copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 100.dp)
        )
    }

}