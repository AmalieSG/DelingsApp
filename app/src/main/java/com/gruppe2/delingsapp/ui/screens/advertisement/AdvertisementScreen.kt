package com.gruppe2.delingsapp.ui.screens.advertisement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.navigation.NavController
//import com.gruppe2.delingsapp.data.model.AdvertisementModel
//import com.gruppe2.delingsapp.data.model.Product
import com.gruppe2.delingsapp.viewmodel.AdvertisementViewModel

/* This screen allows users to fill in the details for creating an advertisement.
The form includes text fields for the title, description,
location (with optional Google Maps API or Nominatim API integration),
category selection, product selection, and availability dates.
 */

/*@Composable
fun AdvertisementScreen(
    navController: NavController,
    viewModel: AdvertisementViewModel = hiltViewModel()
    //viewModel: AdvertisementViewModel<Any?> = hiltViewModel() // benytter hiltViewModel for å sikre såkalt "lifecycle" i dette scopet (Asn)

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

*/



fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

@Composable
fun AdvertisementScreen(
    navController: NavController,
    viewModel: AdvertisementViewModel = hiltViewModel() // benytter hiltViewModel for å sikre såkalt "lifecycle" i dette scopet - såkalt Dependency Injection(Asn)
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

        // Location selection (simplified for now) TODO: Ad.Location: string -> nominatim / maps
        TextField(
            value = viewModel.location,
            onValueChange = { viewModel.location = it },
            label = { Text("Location") }
        )

        // Dropdown for category selection TODO: Ad.DropDown category select (asn)
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

        // Calendar picker for availability (start date and end date) TODO: Ad.CalenderPick (asn)
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




