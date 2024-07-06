package com.zerosword.feature_main.ui

import android.util.Log
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
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.zerosword.domain.extension.urlEncode
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.navigation.Routes
import com.zerosword.domain.state.ToastState
import com.zerosword.feature_main.ui.graph.safeNavigate
import com.zerosword.feature_main.util.extension.toast
import com.zerosword.feature_main.viewmodel.SearchViewModel
import com.zerosword.resources.R
import com.zerosword.resources.R.*
import com.zerosword.resources.ui.compose.LoadingScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

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
                ToastState.API_ERROR -> context.getString(string.network_error_msg)
                ToastState.PAGING_ERROR -> context.getString(string.paging_error_msg)
                ToastState.ADDED_TO_BOOKMARK -> context.getString(string.added_to_bookmark)
                ToastState.DELETED_FROM_BOOKMARK -> context.getString(string.deleted_from_bookmark)
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
            initKeyword = keyword,
            lazyPagingItems = lazyPagingItems,
            listState = viewModel.listState,
            onChangedKeyword = { query ->
                viewModel.searchImage(query = query)
            },
            onBindImage = { imageUrl, model ->
                val isFavoriteState by viewModel.isFavorite(keyword, imageUrl, model.isFavorite)
                    .collectAsState(initial = model.isFavorite)

                SearchItem(
                    keyword = keyword,
                    item = model,
                    isFavoriteState = isFavoriteState,
                    navController = navController
                ) { isFavorite, keyword, url ->
                    val tag = "BOOKMARK"

                    when (isFavorite) {

                        true -> {
                            viewModel.addToFavoriteItem(keyword, url)
                            Log.d(tag, "북마크 추가 -> $url")
                            viewModel.showToast(ToastState.ADDED_TO_BOOKMARK)
                            viewModel.allFavoriteList()
                        }

                        false -> {
                            viewModel.deleteFavoriteItem(keyword, url)
                            Log.d(tag, "북마크 삭제 -> $url")
                            viewModel.showToast(ToastState.DELETED_FROM_BOOKMARK)
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
    initKeyword: String = "",
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
            SearchBar(initKeyword = initKeyword) { query -> onChangedKeyword(query) }
            ImageList(
                lazyPagingItems, listState,
                onBindImage = onBindImage
            )
        }
    }
}

@Composable
private fun SearchBar(initKeyword: String = "", onChangedKeyword: (query: String) -> Unit) {
    val searchQuery = remember { mutableStateOf(TextFieldValue(initKeyword)) }
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
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
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
                singleLine = true,
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
                    selectionColors = TextSelectionColors(
                        handleColor = MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.tertiaryContainer
                    )
                ),
            )
        }
    }
}

@Composable
private fun ImageList(
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

    if (lazyPagingItems.itemCount == 0) EmptyResultScreen()
}

@Composable
private fun SearchItem(
    keyword: String,
    item: KakaoImageModel.DocumentModel,
    isFavoriteState: Boolean,
    navController: NavController,
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
    val scope = rememberCoroutineScope()
    var isClickedItem by remember {
        mutableStateOf(false)
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
                .clickable {
                    scope.launch {
                        if (isClickedItem) return@launch
                        isClickedItem = true
                        val route = Routes.ImageViewer.withArgs(item.imageUrl.urlEncode())
                        navController.safeNavigate(route = route)
                        delay(1000)
                        isClickedItem = false
                    }

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
fun EmptyResultScreen() {
    val context = LocalContext.current
    NotificationScreen(
        mainMessage = context.getString(R.string.empty_result_main_msg),
        subMessage = context.getString(R.string.empty_result_sub_msg),
    )
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