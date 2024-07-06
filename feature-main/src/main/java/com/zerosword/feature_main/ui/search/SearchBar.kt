package com.zerosword.feature_main.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.zerosword.resources.R

@Composable
fun SearchBar(initKeyword: String = "", onChangedKeyword: (query: String) -> Unit) {
    val searchQuery = remember { mutableStateOf(TextFieldValue(initKeyword)) }
    val context = LocalContext.current
    val searchbarBorderRadius = dimensionResource(id = R.dimen.searchbar_border_radius)

    LaunchedEffect(searchQuery.value.text) {
        snapshotFlow { searchQuery.value.text }.collect { query ->
            onChangedKeyword(query)
        }
    }

    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
            )
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                placeholder = {
                    Text(context.getString(R.string.search_place_holder_msg), style = MaterialTheme.typography.bodySmall)
                },
                singleLine = true,
                modifier = Modifier.weight(1f).background(Color.White, shape = RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(searchbarBorderRadius),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    selectionColors = TextSelectionColors(
                        handleColor = MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.tertiaryContainer
                    )
                )
            )
        }
    }
}