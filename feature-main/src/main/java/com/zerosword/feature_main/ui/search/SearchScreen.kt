package com.zerosword.feature_main.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.state.ToastState.*
import com.zerosword.feature_main.ui.NetworkErrorScreen
import com.zerosword.feature_main.ui.NotificationScreen
import com.zerosword.feature_main.util.extension.toast
import com.zerosword.feature_main.viewmodel.SearchViewModel
import com.zerosword.resources.R.*
import com.zerosword.resources.ui.compose.LoadingScreen
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val lazyPagingItems = viewModel.imageSearchResults.collectAsLazyPagingItems()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val isConnected by viewModel.isConnected.collectAsState()

    val keyword by viewModel.searchQuery.collectAsState()

    LaunchedEffect(viewModel.toastState) {
        viewModel.toastState.collect { toastState ->
            val toastMessage = when (toastState) {
                API_ERROR -> context.getString(string.network_error_msg)
                PAGING_ERROR -> context.getString(string.paging_error_msg)
                ADDED_TO_BOOKMARK -> context.getString(string.added_to_bookmark)
                DELETED_FROM_BOOKMARK -> context.getString(string.deleted_from_bookmark)
                else -> ""
            }
            if (toastMessage.isNotEmpty()) context.toast(toastMessage)
        }
    }

    LaunchedEffect(lazyPagingItems.loadState) {
        val loadState = lazyPagingItems.loadState.refresh
        isLoading = loadState is LoadState.Loading
        if (loadState is LoadState.Error) {
            viewModel.showToast(PAGING_ERROR)
        }
    }

    when {
        isConnected -> {
            SearchScreenContent(
                initKeyword = keyword,
                lazyPagingItems = lazyPagingItems,
                listState = viewModel.listState,
                onChangedKeyword = viewModel::searchImage,
                onBindImage = { imageUrl, model ->
                    val isFavoriteState by viewModel.isFavorite(keyword, imageUrl, model.isFavorite)
                        .collectAsState(initial = model.isFavorite)

                    SearchItem(
                        keyword = keyword,
                        item = model,
                        isFavoriteState = isFavoriteState,
                        navController = navController
                    ) { isFavorite, keyword, url ->
                        if (isFavorite) {
                            viewModel.addToFavoriteItem(keyword, url)
                            viewModel.showToast(ADDED_TO_BOOKMARK)
                        } else {
                            viewModel.deleteFavoriteItem(keyword, url)
                            viewModel.showToast(DELETED_FROM_BOOKMARK)
                        }
                    }
                },
                onImageListLoaded = {
                    if (keyword.isEmpty() && lazyPagingItems.itemCount == 0) IntroducingScreen()
                    else if (lazyPagingItems.itemCount == 0) EmptyResultScreen()
                }
            )
            if (isLoading) LoadingScreen()
        }

        else -> NetworkErrorScreen()
    }
}

@Composable
private fun EmptyResultScreen() {
    val context = LocalContext.current
    NotificationScreen(
        mainMessage = context.getString(string.empty_result_main_msg),
        subMessage = context.getString(string.empty_result_sub_msg)
    )
}

@Composable
private fun IntroducingScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    model = mipmap.round_app_icon,
                    contentDescription = null
                )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.width(250.dp),
                text = context.getString(string.how_to_search_image),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSecondary),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.width(250.dp),
                text = context.getString(string.how_to_use_bookmark),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSecondary),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SearchScreenPreview() {
    val lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel> =
        flowOf(PagingData.from(listOf<KakaoImageModel.DocumentModel>())).collectAsLazyPagingItems()
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