package com.zerosword.feature_main.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.zerosword.resources.R

@Composable
fun EditModeButton(onButtonClicked: (isEditMode: Boolean) -> Unit) {

    val context = LocalContext.current
    val selectText = context.getString(R.string.select_button_text)
    val cancelText = context.getString(R.string.cancel_button_text)
    val roundedCorner = dimensionResource(id = R.dimen.button_border_radius)
    var isEditMode by remember { mutableStateOf(false) }

    val selectIcon = Icons.Rounded.Check

    Row(modifier = Modifier
        .wrapContentWidth()
        .clickable {
            isEditMode = !isEditMode
            onButtonClicked(isEditMode)
        }
        .background(
            if (isEditMode) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
            RoundedCornerShape(roundedCorner)
        )
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = selectIcon,
            contentDescription = null,
            tint = if (isEditMode) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary
        )

        Text(
            text = if (isEditMode) cancelText else selectText,
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