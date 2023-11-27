package com.example.hotels.ui.rooms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotels.data.HotelRepository
import com.example.hotels.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

sealed interface RoomsUiState {
    data class Success(val listOfRooms: List<Room>) : RoomsUiState
    object Error : RoomsUiState
    object Loading : RoomsUiState
}

@HiltViewModel
class RoomsViewModel @Inject constructor(private val hotelRepository: HotelRepository) : ViewModel() {
    var roomsUiState: RoomsUiState by mutableStateOf(RoomsUiState.Loading)
        private set
    fun getRooms() {
        viewModelScope.launch {
            roomsUiState = RoomsUiState.Loading
            roomsUiState = try {
                RoomsUiState.Success(hotelRepository.getRooms().rooms)
            } catch (e: Exception) {
                RoomsUiState.Error
            } catch (e: HttpException) {
                RoomsUiState.Error
            }
        }
    }
}