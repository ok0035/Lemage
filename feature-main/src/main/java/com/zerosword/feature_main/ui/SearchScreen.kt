package com.zerosword.feature_main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.zerosword.feature_main.R
import com.zerosword.feature_main.viewmodel.MainViewModel
import com.zerosword.resources.R.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@Composable
fun SearchScreen(viewModel: MainViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        SearchBar(viewModel = viewModel)
        ImageViewer(viewModel = viewModel)

    }
}

@OptIn(FlowPreview::class)
@Composable
fun SearchBar(viewModel: MainViewModel = hiltViewModel()) {
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    LaunchedEffect(key1 = searchQuery.value.text) {

        snapshotFlow { searchQuery.value.text }
            .debounce(1000)
            .collect { query ->
                if (query.isNotEmpty()) viewModel.searchImage(query = query, page = 1)
            }
    }

    TextField(
        value = searchQuery.value,
        onValueChange = { searchQuery.value = it },
        placeholder = {
            Text(
                context.getString(string.search_place_holder_msg)
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
fun ImageViewer(viewModel: MainViewModel) {

    val lazyPagingItems = viewModel.imageSearchResults.collectAsLazyPagingItems()

    LazyColumn {
        items(
            count = lazyPagingItems.itemCount,
        ) { index ->
            println("item -> ${lazyPagingItems[index]?.displaySiteName} $index")
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
fun SearchScreenPreview() {
    SearchScreen()
}