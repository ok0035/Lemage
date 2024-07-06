package com.zerosword.feature_main.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.feature_main.util.extension.urlEncode
import com.zerosword.domain.navigation.Routes
import com.zerosword.feature_main.ui.graph.safeNavigate
import com.zerosword.resources.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RowScope.FavoriteItem(
    item: FavoriteModel,
    navController: NavController,
    isEditMode: Boolean,
    onSelectButtonClicked: (isSelect: Boolean, keyword: String, imageUrl: String) -> Unit
) {
    val borderRadius = dimensionResource(id = R.dimen.image_border_radius)
    var isImageSelected by remember { mutableStateOf(item.isSelect) }
    val (selectionIcon, selectionColor) = remember(isImageSelected) {
        if (isImageSelected)
            Icons.Rounded.Check to Color.White
        else
            Icons.Rounded.Check to Color.White.copy(alpha = 0.8f)
    }

    var isShowDetailImageClicked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val selectionBackColor = remember(isImageSelected) {
        item.isSelect = isImageSelected
        if (isImageSelected)
            Color.Red.copy(alpha = 0.8f)
        else Color.Black.copy(alpha = 0.8f)
    }

    LaunchedEffect(item.isSelect) {
        isImageSelected = item.isSelect
    }

    LaunchedEffect(isEditMode) {
        if (!isEditMode) {
            isImageSelected = false
            onSelectButtonClicked(false, item.keyword, item.imageUrl)
        }
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
                    colorScheme.errorContainer,
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
                    if (!isEditMode)
                        scope.launch {
                            if (isShowDetailImageClicked) return@launch
                            isShowDetailImageClicked = true
                            navController.safeNavigate(Routes.ImageViewer.withArgs(item.imageUrl.urlEncode()))
                            delay(1000)
                            isShowDetailImageClicked = false
                        }
                    else {
                        isImageSelected = !isImageSelected
                        item.isSelect = isImageSelected
                        onSelectButtonClicked(isImageSelected, item.keyword, item.imageUrl)
                    }
                }
        )

        if (isEditMode && isImageSelected)
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(
                        Color.White.copy(alpha = 0.7f),
                        RoundedCornerShape(borderRadius)
                    )
                    .clip(RoundedCornerShape(borderRadius))
            )

        if (isEditMode)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(35.dp)
                    .background(selectionBackColor, CircleShape)
                    .clip(CircleShape)
                    .clickable {
                        isImageSelected = !isImageSelected
                        item.isSelect = isImageSelected
                        onSelectButtonClicked(isImageSelected, item.keyword, item.imageUrl)
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
fun ChunkedFavoriteItems(
    chunkSize: Int = 3,
    list: List<FavoriteModel>,
    navController: NavController,
    isEditMode: Boolean,
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
                isEditMode = isEditMode,
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
