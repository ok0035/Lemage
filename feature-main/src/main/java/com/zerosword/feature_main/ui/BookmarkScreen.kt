package com.zerosword.feature_main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zerosword.data.database.entity.FavoriteEntity
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.feature_main.viewmodel.BookmarkViewModel
import com.zerosword.resources.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel = hiltViewModel()) {

    val favoritesGroupedByKeyword by viewModel.favoritesByKeyword.collectAsState()

    LaunchedEffect(favoritesGroupedByKeyword) {
        viewModel.loadFavoritesByKeyword()
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (titleRef, listRef) = createRefs()
        Box(modifier = Modifier.constrainAs(titleRef) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }) {
            Header(keyword = "asdf")
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .constrainAs(listRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(titleRef.bottom)
                    bottom.linkTo(parent.bottom)
                },
            columns = StaggeredGridCells.Fixed(2),
            state = viewModel.gridState
        ) {
            favoritesGroupedByKeyword.forEach { (keyword, favorites) ->
                items(favorites.count()) { index ->
                    FavoriteItem(favorites[index])
                }
            }
        }
    }

}

@Composable
fun Header(keyword: String) {
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
fun FavoriteItem(favorite: FavoriteModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        ImageItem(item = favorite, isSelectState = false) { isSelect, keyword, imageUrl ->

        }
    }
}

@Composable
private fun ImageItem(
    item: FavoriteModel,
    isSelectState: Boolean,
    onSelectButtonClicked: (isSelect: Boolean, keyword: String, imageUrl: String) -> Unit
) {
    val borderRadius = dimensionResource(id = R.dimen.image_border_radius)
    var isSelect by remember { mutableStateOf(isSelectState) }
    val (favoriteIcon, favoriteColor) = remember(isSelect) {
        if (isSelect) {
            Icons.Rounded.Favorite to Color.Red
        } else {
            Icons.Rounded.FavoriteBorder to Color.White
        }
    }

    LaunchedEffect(isSelectState) {
        isSelect = isSelectState
        item.isSelect = isSelectState
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
                    isSelect = !isSelect
                    onSelectButtonClicked(isSelect, item.keyword, item.imageUrl)
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