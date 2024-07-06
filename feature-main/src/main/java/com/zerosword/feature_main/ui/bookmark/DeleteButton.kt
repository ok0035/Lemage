package com.zerosword.feature_main.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.zerosword.resources.R

@Composable
fun DeleteButton(onDeleteButtonClicked: () -> Unit) {

    val context = LocalContext.current
    val deleteText = context.getString(R.string.delete_button_text)
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