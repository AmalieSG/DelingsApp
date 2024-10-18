package com.gruppe2.delingsapp.ui.screens.advertisement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
//import com.gruppe2.delingsapp.data.model.AdvertisementModel
//import com.gruppe2.delingsapp.data.model.Product
import com.gruppe2.delingsapp.viewmodel.User
import com.gruppe2.delingsapp.viewmodel.AdvertisementViewModel



/*@Composable
fun AdvertisementScreen(username: String?, navController: NavController, advertisementViewModel: AdvertisementViewModel) {
    //var advertisement by remember { mutableStateOf(Advertisement("", "", "", "", " ", " ", products = , photos = ))}
    var advertisement by remember { mutableStateOf(CreateAdvertisement("", "",  products = , photos = ))}

    //var user by remember { mutableStateOf<User?>(null) }
    //var adName by remember { mutableStateOf(TextFieldValue()) }
    //var description by remember { mutableStateOf(TextFieldValue()) }
    //var location by remember { mutableStateOf(TextFieldValue()) }
    //var category by remember { mutableStateOf("General") }
    //val selectedProducts = remember { mutableStateListOf<Product>() } // TODO: Andrea holder på med denne komponenten.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

     */

@JvmOverloads
@Composable
fun AdvertisementScreen(
    navController: NavController,
    viewModel: AdvertisementViewModel<Any?> = hiltViewModel() // benytter hiltViewModel for å sikre såkalt "lifecycle" i dette scopet (Asn)
) {
    Column {
        TextField(
            value = viewModel.title,
            onValueChange = { viewModel.title = it },
            label = { Text("Title") }
        )

        TextField(
            value = viewModel.description,
            onValueChange = { viewModel.description = it },
            label = { Text("Description") }
        )

        // Location selection (simplified for now)
        TextField(
            value = viewModel.location,
            onValueChange = { viewModel.location = it },
            label = { Text("Location") }
        )

        // Dropdown for category selection
        DropdownMenu(
            expanded = /* logic for expanded state */,
            onDismissRequest = { /* logic to close */ }
        ) {
            listOf("Electronics", "Furniture", "Vehicles").forEach { categoryOption ->
                DropdownMenuItem(onClick = { viewModel.category = categoryOption }) {
                    Text(categoryOption)
                }
            }
        }

        // Display products from user's inventory (simulated here)
        LazyColumn {
            items(viewModel.selectedProducts) { product ->
                Text(product.name)
            }
        }

        // Calendar picker for availability (start date and end date)
        Button(onClick = { /* logic to show date picker */ }) {
            Text("Set Availability")
        }

        // Button to create advertisement
        Button(onClick = {
            viewModel.createAdvertisement(
                onSuccess = { navController.popBackStack() },
                onFailure = { exception -> /* Show error */ }
            )
        }) {
            Text("Create Advertisement")
        }
    }
}




