package com.example.delingsapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String, // Motta søkestrengen som parameter
    onQueryChange: (String) -> Unit, // Callback for å oppdatere søkestrengen
    onSearchTriggered: () -> Unit // Callback for å trigge søket
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { onQueryChange(it) }, // Kall funksjonen for å oppdatere søkestrengen
            placeholder = { Text(text = "Search products...") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Når brukeren trykker på "Enter"
                    onSearchTriggered() // Kall funksjonen for å trigge søket
                }
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            )
        )
        IconButton(
            onClick = {
                // Når brukeren trykker på søkeikonet
                onSearchTriggered() // Kall funksjonen for å trigge søket
            }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
