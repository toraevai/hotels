package com.example.hotels.ui.hotel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotels.data.HotelRepository
import com.example.hotels.model.Hotel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

sealed interface HotelUiState {
    data class Success(val hotel: Hotel) : HotelUiState
    object Error : HotelUiState
    object Loading : HotelUiState
}

@HiltViewModel
class HotelViewModel @Inject constructor(private val hotelRepository: HotelRepository) : ViewModel() {
    var hotelUiState: HotelUiState by mutableStateOf(HotelUiState.Loading)
        private set

    init {
        getHotel()
    }

    fun getHotel() {
        viewModelScope.launch {
            hotelUiState = HotelUiState.Loading
            hotelUiState = try {
                HotelUiState.Success(hotelRepository.getHotel())
            } catch (e: Exception) {
                HotelUiState.Error
            } catch (e: HttpException) {
                HotelUiState.Error
            }
        }
    }
}