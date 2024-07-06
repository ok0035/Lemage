package com.zerosword.feature_main.ui.bookmark

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.resources.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreenContent(
    favoritesByKeyword: Map<String, List<FavoriteModel>> = mapOf(Pair("", listOf())),
    listState: LazyListState = rememberLazyListState(),
    chunkSize: Int = 3,
    navController: NavController,
    onItemSelectButtonClicked: (isSelect: Boolean, keyword: String, imageUrl: String) -> Unit,
    onDeleteButtonClicked: () -> Unit
) {
    val headerHeight = dimensionResource(id = R.dimen.bookmark_header_height)
    var isEditMode by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            favoritesByKeyword.forEach { (keyword, favorites) ->
                stickyHeader { BookmarkHeader(keyword = keyword) }
                items(favorites.chunked(chunkSize)) { rowFavorites ->
                    ChunkedFavoriteItems(
                        chunkSize = chunkSize,
                        list = rowFavorites,
                        navController = navController,
                        isEditMode = isEditMode,
                    ) { isSelect, keyword, imageUrl ->
                        onItemSelectButtonClicked(isSelect, keyword, imageUrl)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .constrainAs(createRef()) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 4.dp)
                    height = Dimension.value(headerHeight)
                    width = Dimension.wrapContent
                }
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EditModeButton {
                isEditMode = it
            }
            if (isEditMode) {
                Spacer(modifier = Modifier.width(4.dp))
                DeleteButton {
                    onDeleteButtonClicked()
                }
            }
        }
    }
}