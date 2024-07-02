package com.zerosword.feature_main.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
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
import com.zerosword.feature_main.R
import com.zerosword.feature_main.util.extension.toast
import com.zerosword.feature_main.viewmodel.SearchViewModel
import com.zerosword.resources.R.*
import com.zerosword.resources.ui.compose.LoadingScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel> =
        viewModel.imageSearchResults.collectAsLazyPagingItems()
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val isConnected = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.toastState.collect {
            val toastMessage = when (it) {
                ToastState.API_ERROR ->
                    context.getText(string.network_error_msg).toString()

                ToastState.PAGING_ERROR ->
                    context.getText(string.paging_error_msg).toString()

                else -> ""
            }
            if (toastMessage.isNotEmpty()) context.toast(toastMessage)
        }

    }

    LaunchedEffect(lazyPagingItems.loadState) {
        val loadState = lazyPagingItems.loadState.refresh
        isLoading.value = loadState is LoadState.Loading
        if (loadState is LoadState.Error) {
            viewModel.error(ToastState.PAGING_ERROR)
        }
    }

    LaunchedEffect(viewModel.isConnected) {
        viewModel.isConnected.collectLatest {
            isConnected.value = it
        }
    }

    if (isConnected.value)
        SearchScreenContent(
            viewModel.searchQuery,
            lazyPagingItems = lazyPagingItems,
            listState = viewModel.listState
        ) { query -> viewModel.searchImage(query = query) }

    if (isLoading.value && isConnected.value) LoadingScreen()
    if (!isConnected.value) NetworkErrorScreen()
}

@Composable
private fun SearchScreenContent(
    queryFlow: StateFlow<String>,
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyStaggeredGridState,
    onChangedKeyword: (query: String) -> Unit
) {
    val query = queryFlow.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            SearchBar { query -> onChangedKeyword(query) }
            ImageViewer(query.value, lazyPagingItems, listState)
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
            .fillMaxWidth()
            .padding(12.dp)
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

@Composable
private fun ImageViewer(
    query: String,
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyStaggeredGridState
) {

    if (lazyPagingItems.loadState.append.endOfPaginationReached) {
        Log.d("Paging", "End of pagination reached")
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = listState,
        modifier = Modifier.padding(8.dp)
    ) {
        items(
            count = lazyPagingItems.itemCount,
        ) { index ->

            val imageUrl = lazyPagingItems[index]?.imageUrl ?: ""
            if (imageUrl.isNotEmpty())
                ImageItem(keyword = query, imageUrl = imageUrl) { isFavorite, keyword, imageUrl ->
                    //찜목록 저장
                }
        }
    }
}

@Composable
private fun ImageItem(
    keyword: String,
    imageUrl: String,
    onClickFavorite: (isFavorite: Boolean, keyword: String, imageUrl: String) -> Unit
) {

    val borderRadius = dimensionResource(id = dimen.image_border_radius)
    var isFavorite by remember { mutableStateOf(false) }
    var favoriteIcon by remember { mutableStateOf(Icons.Rounded.FavoriteBorder) }
    var favoriteColor by remember { mutableStateOf(Color.White) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(min = 100.dp)
    ) {
        val imageRef = createRef()
        val favoriteButtonRef = createRef()

        AsyncImage(
            modifier = Modifier
                .heightIn(min = 100.dp)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(4.dp)
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    RoundedCornerShape(borderRadius)
                )
                .clip(RoundedCornerShape(borderRadius)),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Box(
            modifier = Modifier
                .width(35.dp)
                .height(35.dp)
                .background(Color.Black.copy(0.2f), CircleShape)
                .clip(CircleShape)
                .constrainAs(favoriteButtonRef) {
                    end.linkTo(imageRef.end, 8.dp)
                    bottom.linkTo(imageRef.bottom, 8.dp)
                }
                .clickable {
                    isFavorite = !isFavorite

                    favoriteIcon = when (isFavorite) {
                        true -> {
                            favoriteColor = Color.Red
                            Icons.Rounded.Favorite
                        }
                        false -> {
                            favoriteColor = Color.White
                            Icons.Rounded.FavoriteBorder
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .width(30.dp),
                imageVector = favoriteIcon,
                contentDescription = null,
                tint = favoriteColor // 원하는 색상으로 변경
            )
        }

    }
}

@Composable
fun NetworkErrorScreen() {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = context.getString(string.unstable_network_msg),
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
            Text(
                text = context.getString(string.auto_refresh_connected),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSecondary)
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
    val searchFlow = MutableStateFlow("")
    Box(Modifier.fillMaxSize()) {
        SearchScreenContent(
            searchFlow,
            lazyPagingItems = lazyPagingItems,
            listState = listState
        ) { _ -> }
    }
}