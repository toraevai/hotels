package com.example.hotels.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotels.model.Hotel
import com.example.hotels.model.HotelInfo
import com.example.hotels.network.HotelApi
import kotlinx.coroutines.launch

class HotelsViewModel : ViewModel() {
    var hotel by mutableStateOf<Hotel>(
        Hotel(
            id = 0,
            name = "test",
            adress = "123",
            minimalPrice = 1500,
            priceForIt = "lol",
            rating = 5,
            ratingName = "cool",
            imageUrls = listOf(),
            aboutTheHotel = HotelInfo(
                description = "cool",
                peculiarities = listOf()
            )
        )
    )

    init {
        getHotel()
    }
    private fun getHotel() {
        viewModelScope.launch {
            hotel = HotelApi.retrofitService.getHotel()
        }
    }
}