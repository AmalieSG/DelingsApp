package com.gruppe2.delingsapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Navn: ${product.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Eier: ${product.owner}")
            Text(text = "Beskrivelse: ${product.description}")
            Text(text = "Pris: ${product.price} kr")
            Text(text = "Lokasjon: ${product.location}")
            Text(text = "Kategori: ${product.category}")
        }
    }
}
