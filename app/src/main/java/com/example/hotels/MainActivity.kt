package com.example.hotels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hotels.ui.HotelRoom
import com.example.hotels.ui.HotelRoomsScreen
import com.example.hotels.ui.HotelScreen
import com.example.hotels.ui.HotelsViewModel
import com.example.hotels.ui.theme.HotelsTheme
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val hotelsViewModel: HotelsViewModel = viewModel()
                    val hotel = hotelsViewModel.hotel
                    val rooms = hotelsViewModel.listOfRooms
                    HotelRoomsScreen(hotel = hotel, hotelRooms = rooms)
                }
            }
        }
    }
}