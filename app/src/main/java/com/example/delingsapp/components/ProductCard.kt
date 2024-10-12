package com.example.delingsapp.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.delingsapp.model.Product

@Composable
fun ProductCard(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Vis første bilde (hvis tilgjengelig)
        if (product.photos.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(data = product.photos.firstOrNull() ?: Uri.EMPTY),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            // Placeholder image hvis det ikke finnes bilder
            Image(
                painter = rememberImagePainter(data = Uri.EMPTY),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
        }

        // Produktinformasjon
        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Price: ${product.price} NOK", style = MaterialTheme.typography.titleSmall)
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Owner: ${product.owner}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Location: ${product.location}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Category: ${product.category}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
