package com.zerosword.feature_main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.domain.extension.urlEncode
import com.zerosword.domain.navigation.Routes
import com.zerosword.feature_main.ui.graph.safeNavigate
import com.zerosword.feature_main.viewmodel.BookmarkViewModel
import com.zerosword.resources.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

    val isInEdit = LocalInspectionMode.current
    var isExistDeleteList by remember { mutableStateOf(false) }
    val isConnected = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (!isInEdit) {
        LaunchedEffect(Unit) {
            viewModel.deleteItemList.collectLatest {
                isExistDeleteList = it.isNotEmpty()
            }
        }

        LaunchedEffect(viewModel.favoritesByKeyword.collectAsState()) {
            viewModel.loadFavoritesByKeyword()
        }

        LaunchedEffect(viewModel.isConnected) {
            viewModel.isConnected.collectLatest {
                isConnected.value = it
            }
        }
    }

    if (isConnected.value && !isInEdit) {
        BookmarkScreenContent(
            navController = navController,
            favoritesByKeyword = viewModel.favoritesByKeyword.collectAsState().value,
            listState = viewModel.listState,
            isExistDeleteList = isExistDeleteList,
            chunkSize = 3,
            onDeleteButtonClicked = {
                viewModel.deleteItems()
            },
            onBindImage = { isSelect, keyword, imageUrl ->
                scope.launch {
                    viewModel.findItem(keyword, imageUrl).collectLatest { model ->
                        model?.let {
                            model.isSelect = isSelect
                            if (isSelect) viewModel.addItemToDeleteList(model)
                            else viewModel.deleteItemToDeleteList(model)
                        }
                    }
                }
            }
        )
    }

    if (!isConnected.value) NetworkErrorScreen()
    if (!isInEdit) {
        val favoritesByKeyword by viewModel.favoritesByKeyword.collectAsState()
        if(favoritesByKeyword.isEmpty()) EmptyBookmarkScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BookmarkScreenContent(
    favoritesByKeyword: Map<String, List<FavoriteModel>> = mapOf(Pair("", listOf())),
    listState: LazyListState = rememberLazyListState(),
    chunkSize: Int = 3,
    navController: NavController,
    isExistDeleteList: Boolean = false,
    onBindImage: (isSelect: Boolean, keyword: String, imageUrl: String) -> Unit,
    onDeleteButtonClicked: () -> Unit
) {

    val headerHeight = dimensionResource(id = R.dimen.bookmark_header_height)

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
                stickyHeader { Header(keyword = keyword) }
                items(favorites.chunked(chunkSize)) { rowFavorites ->

                    ChunkedFavoriteItems(
                        chunkSize = chunkSize,
                        list = rowFavorites,
                        navController = navController
                    ) { isSelect, keyword, imageUrl ->
                        onBindImage(isSelect, keyword, imageUrl)
                    }
                }
            }
        }

        if (isExistDeleteList)
            Box(
                modifier = Modifier
                    .constrainAs(createRef()) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 4.dp)
                        height = Dimension.value(headerHeight)
                        width = Dimension.wrapContent
                    }
                    .wrapContentWidth(),
                contentAlignment = Alignment.Center
            ) {
                DeleteButton {
                    onDeleteButtonClicked()
                }
            }

    }
}

@Composable
private fun ChunkedFavoriteItems(
    chunkSize: Int = 3,
    list: List<FavoriteModel>,
    navController: NavController,
    onSelectButtonClicked: (isSelect: Boolean, keyword: String, imageUrl: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        list.forEach { model ->
            FavoriteItem(
                item = model,
                navController = navController,
                onSelectButtonClicked = { isSelect: Boolean, keyword: String, imageUrl: String ->
                    onSelectButtonClicked(isSelect, keyword, imageUrl)
                }
            )
        }

        repeat(chunkSize - list.size) {
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}

@Composable
private fun Header(keyword: String) {

    val headerHeight = dimensionResource(id = R.dimen.bookmark_header_height)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = keyword,
            style = MaterialTheme
                .typography
                .headlineSmall
                .copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 100.dp)
        )
    }

}

@Composable
private fun DeleteButton(onDeleteButtonClicked: () -> Unit) {

    val context = LocalContext.current
    val deleteText = context.getText(R.string.delete_button_text).toString()
    val roundedCorner = dimensionResource(id = R.dimen.button_border_radius)

    val deleteIcon = Icons.Rounded.Delete

    Row(modifier = Modifier
        .wrapContentWidth()
        .clickable {
            onDeleteButtonClicked()
        }
        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(roundedCorner))
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = deleteIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )

        Text(
            text = deleteText,
            style = MaterialTheme
                .typography
                .labelLarge
                .copy(color = MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    Color.Transparent
                )
        )
    }
}

@Composable
private fun RowScope.FavoriteItem(
    item: FavoriteModel,
    navController: NavController,
    onSelectButtonClicked: (isSelect: Boolean, keyword: String, imageUrl: String) -> Unit
) {
    val borderRadius = dimensionResource(id = R.dimen.image_border_radius)
    var isSelect by remember { mutableStateOf(item.isSelect) }
    val (selectionIcon, selectionColor) = remember(isSelect) {
        if (isSelect)
            Icons.Rounded.Check to Color.White
        else
            Icons.Rounded.Check to Color.White.copy(alpha = 0.8f)
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val selectionBackColor = remember(isSelect) {
        item.isSelect = isSelect
        if (isSelect)
            Color.Red.copy(alpha = 0.8f)
        else Color.Black.copy(alpha = 0.8f)
    }

    ConstraintLayout(
        modifier = Modifier
            .weight(1f)
            .wrapContentHeight()
            .heightIn(min = 100.dp)
            .padding(4.dp)
    ) {
        val (imageRef, bookmarkButtonRef) = createRefs()

        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .heightIn(min = 100.dp)
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
                        if (isClicked) return@launch
                        isClicked = true
                        navController.safeNavigate(Routes.ImageViewer.withArgs(item.imageUrl.urlEncode()))
                        delay(1000)
                        isClicked = false
                    }

                }
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(35.dp)
                .background(selectionBackColor, CircleShape)
                .clip(CircleShape)
                .clickable {
                    isSelect = !isSelect
                    item.isSelect = isSelect
                    onSelectButtonClicked(isSelect, item.keyword, item.imageUrl)
                }
                .constrainAs(bookmarkButtonRef) {
                    end.linkTo(imageRef.end, margin = 8.dp)
                    bottom.linkTo(imageRef.bottom, margin = 8.dp)
                }
        ) {
            Icon(
                imageVector = selectionIcon,
                contentDescription = null,
                tint = selectionColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Composable
fun EmptyBookmarkScreen() {
    val context = LocalContext.current
    NotificationScreen(
        mainMessage = context.getString(R.string.no_bookmark_screen_main_msg),
        subMessage = context.getString(R.string.no_bookmark_screen_sub_msg)
    )
}

@Composable
@Preview(showBackground = true)
private fun BookmarkScreenPreview() {
    BookmarkScreenContent(
        favoritesByKeyword = mapOf(
            Pair(
                "테스트", listOf(
                    FavoriteModel("테스트", imageUrl = "")
                )
            )
        ),
        navController = rememberNavController(),
        onDeleteButtonClicked = {},
        onBindImage = { isSelect, keyword, imageUrl ->

        }
    )
}