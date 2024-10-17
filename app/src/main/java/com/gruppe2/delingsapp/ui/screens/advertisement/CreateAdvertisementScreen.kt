package com.gruppe2.delingsapp.ui.screens.advertisement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.gruppe2.delingsapp.viewmodel.createAdvertisement
import com.gruppe2.delingsapp.viewmodel.sampleProducts


@Composable
fun CreateAdvertisementScreen() {
    var adName by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var location by remember { mutableStateOf(TextFieldValue()) }
    var category by remember { mutableStateOf("General") }
    val selectedProducts = remember { mutableStateListOf<Product>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create an Advertisement", style = MaterialTheme.typography.titleLarge)

        // Input fields for advertisement details
        OutlinedTextField(
            value = adName,
            onValueChange = { adName = it },
            label = { Text("Advertisement Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Category Dropdown
        Text("Select a Category")
        // Use DropdownMenu or other component to select a category

        // Display user's products and allow selection
        Text("Select Products to Include:")
        for (product in sampleProducts) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        if (selectedProducts.contains(product)) {
                            selectedProducts.remove(product)
                        } else {
                            selectedProducts.add(product)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedProducts.contains(product),
                    onCheckedChange = {
                        if (it) selectedProducts.add(product) else selectedProducts.remove(product)
                    }
                )
                Text(product.name, modifier = Modifier.padding(start = 8.dp))
            }
        }

        // Button to create the advertisement
        Button(
            onClick = {
                createAdvertisement(
                    owner = "User", // Replace with actual user data
                    name = adName.text,
                    description = description.text,
                    location = location.text,
                    category = category,
                    selectedProducts = selectedProducts.toList()
                )
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Create Advertisement")
        }
    }
}









//Skjerm hvor bruker skal kunne fylle inn informasjon for å opprette en Annonnse

/*@Composable
fun CreateAdvertisement() {


    Column(
        modifier = Modifier
            .fillMaxSize()
        //verticalArrangement =  Arrangement.Top
    )

}

@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
} */



//Pseudo code





//Input fields:

/*

1.

 */


//
