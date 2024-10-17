package com.gruppe2.delingsapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { onQueryChange(it) },
        placeholder = { Text(text = "Search products...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchTriggered()
            }
        ),
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    SearchBar(
        searchQuery = "",
        onQueryChange = {},
        onSearchTriggered = {}
    )
}
