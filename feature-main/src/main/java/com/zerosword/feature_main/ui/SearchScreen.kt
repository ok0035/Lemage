package com.zerosword.feature_main.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.state.ToastState
import com.zerosword.feature_main.util.extension.toast
import com.zerosword.feature_main.viewmodel.SearchViewModel
import com.zerosword.resources.R.*
import com.zerosword.resources.ui.compose.LoadingScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel> =
        viewModel.imageSearchResults.collectAsLazyPagingItems()
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val isConnected = remember { mutableStateOf(false) }
    var keyword by remember { mutableStateOf(viewModel.searchQuery.value) }

    LaunchedEffect(viewModel.searchQuery) {
        keyword = viewModel.searchQuery.value
    }

    LaunchedEffect(Unit) {
        viewModel.toastState.collect {

            val toastMessage = when (it) {
                ToastState.API_ERROR -> context.getText(string.network_error_msg).toString()
                ToastState.PAGING_ERROR -> context.getText(string.paging_error_msg).toString()
                else -> ""
            }
            if (toastMessage.isNotEmpty()) context.toast(toastMessage)
        }

    }

    LaunchedEffect(lazyPagingItems.loadState) {
        val loadState = lazyPagingItems.loadState.refresh
        isLoading.value = loadState is LoadState.Loading
        if (loadState is LoadState.Error) {
            viewModel.showToast(ToastState.PAGING_ERROR)
        }
    }

    LaunchedEffect(viewModel.isConnected) {
        viewModel.isConnected.collectLatest {
            isConnected.value = it
        }
    }

    if (isConnected.value)
        SearchScreenContent(
            lazyPagingItems = lazyPagingItems,
            listState = viewModel.listState,
            onChangedKeyword = { query ->
                if (query.isNotEmpty()) viewModel.searchImage(query = query)
            },
            onBindImage = { imageUrl, model ->
                val isFavoriteState by viewModel.isFavorite(keyword, imageUrl, model.isFavorite)
                    .collectAsState(initial = model.isFavorite)

                FavoriteItem(
                    keyword = keyword,
                    item = model,
                    isFavoriteState = isFavoriteState
                ) { isFavorite, keyword, url ->
                    val tag = "BOOKMARK"

                    when (isFavorite) {

                        true -> {
                            viewModel.addToFavoriteItem(keyword, url)
                            Log.d(tag, "북마크 추가 -> $url")
                            viewModel.allFavoriteList()
                        }

                        false -> {
                            viewModel.deleteFavoriteItem(keyword, url)
                            Log.d(tag, "북마크 삭제 -> $url")
                            viewModel.allFavoriteList()
                        }

                    }
                }
            }
        )

    if (isLoading.value && isConnected.value) LoadingScreen()
    if (!isConnected.value) NetworkErrorScreen()
}

@Composable
private fun SearchScreenContent(
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyStaggeredGridState,
    onChangedKeyword: (query: String) -> Unit,
    onBindImage: @Composable (imageUrl: String, model: KakaoImageModel.DocumentModel) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            SearchBar { query -> onChangedKeyword(query) }
            ImageViewer(
                lazyPagingItems, listState,
                onBindImage = onBindImage
            )
        }
    }
}

@Composable
private fun SearchBar(onChangedKeyword: (query: String) -> Unit) {
    val searchQuery = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    val searchbarBorderRadius = dimensionResource(id = dimen.searchbar_border_radius)

    LaunchedEffect(key1 = searchQuery.value.text) {
        snapshotFlow { searchQuery.value.text }
            .collect { query ->
                onChangedKeyword(query)
            }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = null
            )
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                placeholder = {
                    Text(
                        context.getString(string.search_place_holder_msg),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(searchbarBorderRadius),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }
    }
}

@Composable
private fun ImageViewer(
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyStaggeredGridState,
    onBindImage: @Composable (imageUrl: String, model: KakaoImageModel.DocumentModel) -> Unit
) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = listState,
        modifier = Modifier.padding(8.dp)
    ) {
        items(
            count = lazyPagingItems.itemCount,
        ) { index ->
            val item = lazyPagingItems[index]
            val imageUrl = item?.imageUrl ?: ""
            item?.let { model ->
                onBindImage(imageUrl, model)
            }

        }
    }
}

@Composable
private fun FavoriteItem(
    keyword: String,
    item: KakaoImageModel.DocumentModel,
    isFavoriteState: Boolean,
    onBookmarkButtonClicked: (isFavorite: Boolean, keyword: String, imageUrl: String) -> Unit
) {
    val borderRadius = dimensionResource(id = dimen.image_border_radius)
    var isFavorite by remember { mutableStateOf(isFavoriteState) }
    val (favoriteIcon, favoriteColor) = remember(isFavorite) {
        if (isFavorite) {
            Icons.Rounded.Favorite to Color.Red
        } else {
            Icons.Rounded.FavoriteBorder to Color.White
        }
    }

    LaunchedEffect(isFavoriteState) {
        isFavorite = isFavoriteState
        item.isFavorite = isFavoriteState
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(min = 100.dp)
    ) {
        val (imageRef, favoriteButtonRef) = createRefs()

        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .heightIn(min = 100.dp)
                .padding(4.dp)
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    RoundedCornerShape(borderRadius)
                )
                .clip(RoundedCornerShape(borderRadius))
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(35.dp)
                .background(Color.Black.copy(alpha = 0.2f), CircleShape)
                .clip(CircleShape)
                .clickable {
                    isFavorite = !isFavorite
                    onBookmarkButtonClicked(isFavorite, keyword, item.imageUrl)
                }
                .constrainAs(favoriteButtonRef) {
                    end.linkTo(imageRef.end, margin = 8.dp)
                    bottom.linkTo(imageRef.bottom, margin = 8.dp)
                }
        ) {
            Icon(
                imageVector = favoriteIcon,
                contentDescription = null,
                tint = favoriteColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SearchScreenPreview() {

    val lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel> =
        flowOf(
            PagingData.from(listOf<KakaoImageModel.DocumentModel>())
        ).collectAsLazyPagingItems()

    val listState = rememberLazyStaggeredGridState()
    Box(Modifier.fillMaxSize()) {
        SearchScreenContent(
            lazyPagingItems = lazyPagingItems,
            listState = listState,
            onChangedKeyword = { _ -> },
            onBindImage = { _, _ -> }
        )
    }
}