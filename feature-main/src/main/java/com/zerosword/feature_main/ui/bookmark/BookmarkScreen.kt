package com.zerosword.feature_main.ui.bookmark

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.domain.state.ToastState.*
import com.zerosword.feature_main.ui.NetworkErrorScreen
import com.zerosword.feature_main.util.extension.toast
import com.zerosword.feature_main.viewmodel.BookmarkViewModel
import com.zerosword.resources.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isInEdit = LocalInspectionMode.current
    var isExistDeleteList by remember { mutableStateOf(false) }
    val isConnected = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (!isInEdit) {

        LaunchedEffect(Unit) {
            viewModel.toastState.collect {

                val toastMessage = when (it) {
                    DELETED_SELECTED_ITEM_FROM_BOOKMARK -> {
                        val selectedItemCount = viewModel.deleteItemList.value.size
                        String.format(
                            context.getString(R.string.deleted_n_items_from_bookmark),
                            selectedItemCount
                        )
                    }

                    NO_DELETABLE_ITEMS -> context.getString(R.string.no_deletable_items)
                    PAGING_ERROR -> context.getString(R.string.paging_error_msg)
                    ADDED_TO_BOOKMARK -> context.getString(R.string.added_to_bookmark)
                    DELETED_FROM_BOOKMARK -> context.getString(R.string.deleted_from_bookmark)
                    else -> ""
                }
                if (toastMessage.isNotEmpty()) context.toast(toastMessage)
            }

        }

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
        val chunkSize = when (configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 3
            Configuration.ORIENTATION_LANDSCAPE -> 8
            else -> 3
        }
        BookmarkScreenContent(
            navController = navController,
            favoritesByKeyword = viewModel.favoritesByKeyword.collectAsState().value,
            listState = viewModel.listState,
            chunkSize = chunkSize,
            onDeleteButtonClicked = {
                viewModel.deleteItems()
            },
            onItemSelectButtonClicked = { isSelect, keyword, imageUrl ->
                scope.launch {
                    viewModel.findItem(keyword, imageUrl).collectLatest { model ->
                        model?.let {
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
        if (favoritesByKeyword.isEmpty()) EmptyBookmarkScreen()
    }
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
        onItemSelectButtonClicked = { _, _, _ -> }
    )
}