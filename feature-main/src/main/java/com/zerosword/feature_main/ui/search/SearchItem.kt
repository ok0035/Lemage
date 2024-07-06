package com.zerosword.feature_main.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.zerosword.feature_main.util.extension.urlEncode
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.navigation.Routes
import com.zerosword.feature_main.ui.graph.safeNavigate
import com.zerosword.resources.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchItem(
    keyword: String,
    item: KakaoImageModel.DocumentModel,
    isFavoriteState: Boolean,
    navController: NavController,
    onBookmarkButtonClicked: (isFavorite: Boolean, keyword: String, imageUrl: String) -> Unit
) {
    val borderRadius = dimensionResource(id = R.dimen.image_border_radius)
    var isFavorite by remember { mutableStateOf(isFavoriteState) }
    val (favoriteIcon, favoriteColor) = remember(isFavorite) {
        if (isFavorite) {
            Icons.Rounded.Favorite to Color.Red
        } else {
            Icons.Rounded.FavoriteBorder to Color.White
        }
    }
    val scope = rememberCoroutineScope()
    var isClickedItem by remember { mutableStateOf(false) }

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