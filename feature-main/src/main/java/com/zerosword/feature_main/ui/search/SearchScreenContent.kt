package com.zerosword.feature_main.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.zerosword.domain.model.KakaoImageModel

@Composable
fun SearchScreenContent(
    initKeyword: String = "",
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyStaggeredGridState,
    onChangedKeyword: (query: String) -> Unit = {},
    onBindImage: @Composable (imageUrl: String, model: KakaoImageModel.DocumentModel) -> Unit = { _, _ -> },
    onImageListLoaded: @Composable () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column {
            SearchBar(initKeyword = initKeyword, onChangedKeyword = onChangedKeyword)
            ImageResultList(lazyPagingItems, listState, onBindImage, onImageListLoaded)
        }
    }
}

