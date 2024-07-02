package com.zerosword.feature_main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zerosword.domain.entity.FavoriteEntity
import com.zerosword.feature_main.viewmodel.BookmarkViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel = hiltViewModel()) {

    val favoritesGroupedByKeyword by viewModel.favoritesByKeyword.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        favoritesGroupedByKeyword.forEach { (keyword, favorites) ->
            stickyHeader {
                KeywordHeader(keyword)
            }
            items(favorites) { favorite ->
                FavoriteItem(favorite)
            }
        }
    }
}

@Composable
fun KeywordHeader(keyword: String) {
    Text(
        text = keyword,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(8.dp)
    )
}

@Composable
fun FavoriteItem(favorite: FavoriteEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = favorite.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(4.dp)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeAppTheme {
//        val sampleData = mapOf(
//            "cat" to listOf(
//                Favorite(1, "cat", "https://example.com/cat1.jpg"),
//                Favorite(2, "cat", "https://example.com/cat2.jpg")
//            ),
//            "dog" to listOf(
//                Favorite(3, "dog", "https://example.com/dog1.jpg")
//            )
//        )
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            sampleData.forEach { (keyword, favorites) ->
//                stickyHeader {
//                    KeywordHeader(keyword)
//                }
//                items(favorites) { favorite ->
//                    FavoriteItem(favorite)
//                }
//            }
//        }
//    }
//}

@Composable
@Preview(showBackground = true)
private fun BookmarkScreenPreview() {
    BookmarkScreen()
}