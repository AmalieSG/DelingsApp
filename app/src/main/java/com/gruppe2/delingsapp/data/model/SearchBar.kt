package com.gruppe2.delingsapp.data.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    placeholder: String = "Search...",
    onSearch: (String) -> Unit
) {
    val searchText = remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = searchText.value,
        onValueChange = { newText ->
            searchText.value = newText
            onSearch(newText.text) // Trigge søk når bruker skriver
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.primary
        )
    )
}
