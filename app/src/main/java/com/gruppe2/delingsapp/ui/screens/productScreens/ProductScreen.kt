package com.gruppe2.delingsapp.ui.screens.productScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gruppe2.delingsapp.viewmodel.Product
//import com.example.myapplication.components.Product
import com.gruppe2.delingsapp.viewmodel.ProductViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import kotlinx.coroutines.launch

@Composable
fun DisplayImageFromUrl(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Image from URL",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // This makes the image take up a fixed square area.
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProductScreen(productName: String?, navController: NavController, productViewModel: ProductViewModel) {
    var product by remember { mutableStateOf(Product("", "", "", 0.0, mutableListOf(), "", "", false)) }
    var currentImageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(productName) {
        if (productName != null) {
            val result = productViewModel.getProduct(productName)
            if (result != null) {
                product = result
            } else {
                println("Kunne ikke finne produkt")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = product.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (product.photos.isNotEmpty()) {
            DisplayImageFromUrl(product.photos[currentImageIndex])

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {

                        currentImageIndex = (currentImageIndex - 1 + product.photos.size) % product.photos.size
                    },
                    enabled = product.photos.size > 1
                ) {
                    Icon(modifier = Modifier.size(16.dp),imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {

                        currentImageIndex = (currentImageIndex + 1) % product.photos.size
                    },
                    enabled = product.photos.size > 1
                ) {
                    Icon(modifier = Modifier.size(16.dp),imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                }
            }
        } else {
            Text("Ingen bilder tilgjengelig", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = product.description,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pris: ${product.price} kr",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Eier: ${product.owner}",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Status - leid?: ${product.status}",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kategori: ${product.category}",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("updateProduct/$productName")
            }
        ) {
            Text("Oppdater")
        }
    }
}