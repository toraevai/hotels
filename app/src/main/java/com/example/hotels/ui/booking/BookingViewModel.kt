package com.example.hotels.ui.booking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotels.data.HotelRepository
import com.example.hotels.data.TouristInfo
import com.example.hotels.data.UserInfo
import com.example.hotels.model.BookRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

sealed interface BookingUiState {
    data class Success(val bookRoom: BookRoom) : BookingUiState
    object Error : BookingUiState
    object Loading : BookingUiState
}

@HiltViewModel
class BookingViewModel @Inject constructor(private val hotelRepository: HotelRepository) :
    ViewModel() {
    var bookingUiState: BookingUiState by mutableStateOf(BookingUiState.Loading)
        private set

    private val _userInfo = MutableStateFlow(
        UserInfo(
            phone = "",
            mail = ""
        )
    )
    val userInfo: StateFlow<UserInfo> = _userInfo

    var touristsChecked by mutableStateOf(false)
    var emailChecked by mutableStateOf(false)

    fun getBooking() {
        viewModelScope.launch {
            bookingUiState = BookingUiState.Loading
            bookingUiState = try {
                BookingUiState.Success(hotelRepository.getBooking())
            } catch (e: Exception) {
                BookingUiState.Error
            } catch (e: HttpException) {
                BookingUiState.Error
            }
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

    fun checkEmail() {
        emailChecked = true
    }

    fun touristsFieldsIsEmpty(): Boolean {
        val tourists = _userInfo.value.tourists
        var touristsHaveEmptyField = false
        for (i in tourists.indices) {
            if (tourists[i].name.value.isEmpty()) {
                touristsHaveEmptyField = true
            }
            if (tourists[i].surname.value.isEmpty()) {
                touristsHaveEmptyField = true
            }
            if (tourists[i].birthday.value.isEmpty()) {
                touristsHaveEmptyField = true
            }
            if (tourists[i].citizenship.value.isEmpty()) {
                touristsHaveEmptyField = true
            }
            if (tourists[i].internationalPassportNumber.value.isEmpty()) {
                touristsHaveEmptyField = true
            }
            if (tourists[i].internationalPassportEndDate.value.isEmpty()) {
                touristsHaveEmptyField = true
            }
            if (_userInfo.value.phone.isEmpty()) {
                touristsHaveEmptyField = true
            }
        }
        touristsChecked = true
        return touristsHaveEmptyField
    }
}