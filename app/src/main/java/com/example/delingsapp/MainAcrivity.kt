package com.example.delingsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.delingsapp.ui.screens.SearchScreen
import com.example.delingsapp.ui.theme.MyAppTheme
import com.example.delingsapp.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen(searchViewModel)
                }
            }
        }
    }
}
