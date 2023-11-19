package com.example.hotels.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotels.data.TouristInfo
import com.example.hotels.data.UserInfo
import com.example.hotels.model.BookRoom
import com.example.hotels.model.Hotel
import com.example.hotels.model.HotelInfo
import com.example.hotels.model.Room
import com.example.hotels.network.HotelApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HotelsViewModel : ViewModel() {
    var hotel by mutableStateOf(
        Hotel(
            id = 0,
            name = "test",
            address = "123",
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

    var listOfRooms by mutableStateOf((listOf<Room>()))

    var booking by mutableStateOf(
        BookRoom(
            id = 0,
            hotelName = "test",
            hotelAddress = "123",
            rating = 5,
            ratingName = "cool",
            departure = "da",
            arrivalCountry = "Pony Land",
            tourDateStart = "Zavtra",
            tourDateStop = "Poslezavtra",
            numberOfNights = 1,
            room = "great",
            nutrition = "no",
            tourPrice = 300,
            fuelCharge = 150,
            serviceCharge = 10
        )
    )

    private val _userInfo = MutableStateFlow(
        UserInfo(
            phone = "",
            mail = ""
        )
    )
    val userInfo: StateFlow<UserInfo> = _userInfo


    init {
        getHotel()
        getRooms()
        getBooking()
    }

    private fun getHotel() {
        viewModelScope.launch {
            hotel = HotelApi.retrofitService.getHotel()
        }
    }

    private fun getRooms() {
        viewModelScope.launch {
            listOfRooms = HotelApi.retrofitService.getRooms().rooms
        }
    }

    private fun getBooking() {
        viewModelScope.launch {
            booking = HotelApi.retrofitService.getBooking()
        }
    }

    fun changePhone(phone: String) {
        _userInfo.update { it.copy(phone = phone) }
    }

    fun changeMail(mail: String) {
        _userInfo.update { it.copy(mail = mail) }
    }

    fun addTourist() {
        _userInfo.value.tourists.add(
            TouristInfo(
                name = mutableStateOf(""),
                surname = mutableStateOf(""),
                birthday = mutableStateOf(""),
                citizenship = mutableStateOf(""),
                internationalPassportNumber = mutableStateOf(""),
                internationalPassportEndDate = mutableStateOf("")
            )
        )
    }

    fun changeTouristName(index: Int, name: String) {
        _userInfo.value.tourists[index].name.value = name
    }

    fun changeTouristSurname(index: Int, surname: String) {
        _userInfo.value.tourists[index].surname.value = surname
    }

    fun changeTouristBirthday(index: Int, birthday: String) {
        _userInfo.value.tourists[index].birthday.value = birthday
    }

    fun changeTouristCitizenship(index: Int, citizenship: String) {
        _userInfo.value.tourists[index].citizenship.value = citizenship
    }

    fun changeTouristPassNumber(index: Int, passNumber: String) {
        _userInfo.value.tourists[index].internationalPassportNumber.value = passNumber
    }

    fun changeTouristPassEndDate(index: Int, endDate: String) {
        _userInfo.value.tourists[index].internationalPassportEndDate.value = endDate
    }
}