package com.example.hotels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.hotels.navigation.HotelsAppNavHost
import com.example.hotels.ui.HotelsViewModel
import com.example.hotels.ui.theme.HotelsTheme

class MainActivity : ComponentActivity() {
//    val hotelsViewModel: HotelsViewModel by ViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            HotelsTheme {
                HotelsAppNavHost(
                    hotelsViewModel = viewModel(),
                    navController = rememberNavController()
                )
            }
        }
    }
}