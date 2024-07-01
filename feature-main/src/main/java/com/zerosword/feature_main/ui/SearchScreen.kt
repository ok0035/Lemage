package com.zerosword.feature_main.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.feature_main.R
import com.zerosword.feature_main.viewmodel.SearchViewModel
import com.zerosword.resources.R.*
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel> =
        viewModel.imageSearchResults.collectAsLazyPagingItems()

    val context = LocalContext.current

    // LoadState 관찰
    LaunchedEffect(lazyPagingItems.loadState) {
        snapshotFlow { lazyPagingItems.loadState }
            .collect { loadStates ->
                if (loadStates.refresh is LoadState.Error) {
                    val error = (loadStates.refresh as LoadState.Error).error
                    Toast.makeText(
                        context,
                        context.getText(string.network_error_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    SearchScreenContent(
        lazyPagingItems = lazyPagingItems,
        listState = viewModel.listState
    ) { query ->
        viewModel.searchImage(query = query)
    }

}

@Composable
private fun SearchScreenContent(
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyListState,
    onChangedKeyword: (query: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        SearchBar { query -> onChangedKeyword(query) }
        ImageViewer(lazyPagingItems, listState)
    }
}

@Composable
private fun SearchBar(onChangedKeyword: (query: String) -> Unit) {
    val searchQuery = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    LaunchedEffect(key1 = searchQuery.value.text) {
        snapshotFlow { searchQuery.value.text }
            .collect { query ->
                if (query.isNotEmpty())
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
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
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
    lazyPagingItems: LazyPagingItems<KakaoImageModel.DocumentModel>,
    listState: LazyListState
) {

    LazyColumn(
        state = listState
    ) {
        items(
            count = lazyPagingItems.itemCount,
        ) { index ->

            val imageUrl = lazyPagingItems[index]?.imageUrl
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
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

    val listState = rememberLazyListState()

    Box(Modifier.fillMaxSize()) {
        SearchScreenContent(
            lazyPagingItems = lazyPagingItems,
            listState = listState
        ) { _ -> }
    }
}