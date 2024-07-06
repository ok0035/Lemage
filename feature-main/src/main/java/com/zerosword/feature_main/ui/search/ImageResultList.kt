package com.zerosword.feature_main.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.zerosword.domain.model.KakaoImageModel

@Composable
fun ImageResultList(
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyStaggeredGridState,
    onBindImage: @Composable (imageUrl: String, model: KakaoImageModel.DocumentModel) -> Unit,
    onComplete: @Composable () -> Unit
) {

    val configuration = LocalConfiguration.current
    val fixedSize = when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> 2
        Configuration.ORIENTATION_LANDSCAPE -> 7
        else -> 2
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(fixedSize),
        state = listState,
        modifier = Modifier.padding(8.dp)
    ) {
        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            val imageUrl = item?.imageUrl ?: ""
            item?.let { model ->
                onBindImage(imageUrl, model)
            }
        }
    }
    onComplete()
}