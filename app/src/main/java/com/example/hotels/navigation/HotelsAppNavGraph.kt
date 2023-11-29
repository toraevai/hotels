package com.example.hotels.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotels.ui.PaidDestination
import com.example.hotels.ui.PaidScreen
import com.example.hotels.ui.booking.BookingDestination
import com.example.hotels.ui.booking.BookingScreen
import com.example.hotels.ui.booking.BookingViewModel
import com.example.hotels.ui.hotel.HotelDestination
import com.example.hotels.ui.hotel.HotelScreen
import com.example.hotels.ui.hotel.HotelUiState
import com.example.hotels.ui.hotel.HotelViewModel
import com.example.hotels.ui.rooms.RoomsDestination
import com.example.hotels.ui.rooms.RoomsScreen
import com.example.hotels.ui.rooms.RoomsViewModel

@Composable
fun HotelsAppNavHost() {
    val hotelViewModel = hiltViewModel<HotelViewModel>()
    val roomsViewModel = hiltViewModel<RoomsViewModel>()
    val bookingViewModel = hiltViewModel<BookingViewModel>()
    val navController = rememberNavController()
    val hotelName: String = when (hotelViewModel.hotelUiState) {
        is HotelUiState.Loading -> ""
        is HotelUiState.Success -> (hotelViewModel.hotelUiState as HotelUiState.Success).hotel.name
        is HotelUiState.Error -> ""
    }
    val user = bookingViewModel.userInfo.collectAsState().value
    NavHost(
        navController = navController,
        startDestination = HotelDestination.route
    ) {
        composable(route = HotelDestination.route) {
            HotelScreen(
                hotelUiState = hotelViewModel.hotelUiState,
                navigateToRooms = {
                    roomsViewModel.getRooms()
                    navController.navigate(RoomsDestination.route)
                },
                retryAction = { hotelViewModel.getHotel() }
            )
        }
        composable(route = RoomsDestination.route) {
            RoomsScreen(
                hotelName = hotelName,
                roomsUiState = roomsViewModel.roomsUiState,
                navigateBack = { navController.navigateUp() },
                onRoomClick = {
                    bookingViewModel.getBooking()
                    navController.navigate(BookingDestination.route)
                },
                retryAction = { roomsViewModel.getRooms() })
        }
        composable(route = BookingDestination.route) {
            BookingScreen(
                bookingUiState = bookingViewModel.bookingUiState,
                userPhone = user.phone,
                userMail = user.mail,
                touristsInfo = user.tourists,
                onUserPhoneChange = { if (it.length <= 10) bookingViewModel.changePhone(it) },
                onUserMailChange = { bookingViewModel.changeMail(it) },
                emailChecked = bookingViewModel.emailChecked,
                checkEmail = { bookingViewModel.checkEmail() },
                addTourist = { bookingViewModel.addTourist() },
                onTouristNameChange = { index, name ->
                    bookingViewModel.changeTouristName(index, name)
                },
                onTouristSurnameChange = { index, surname ->
                    bookingViewModel.changeTouristSurname(index, surname)
                },
                onTouristBirthdayChange = { index, birthday ->
                    bookingViewModel.changeTouristBirthday(index, birthday)
                },
                onTouristCitizenshipChange = { index, citizenship ->
                    bookingViewModel.changeTouristCitizenship(index, citizenship)
                },
                onTouristPassNumChange = { index, passNumber ->
                    bookingViewModel.changeTouristPassNumber(index, passNumber)
                },
                onTouristPassEndDateChange = { index, endDate ->
                    bookingViewModel.changeTouristPassEndDate(index, endDate)
                },
                conditionsChecked = bookingViewModel.touristsChecked,
                onPaidClick = {
                    if (!bookingViewModel.touristsFieldsIsEmpty()) {
                        navController.navigate(PaidDestination.route)
                    }
                },
                navigateBack = { navController.navigateUp() },
                retryAction = { bookingViewModel.getBooking() }
            )
        }
        composable(
            route = PaidDestination.route
        ) {
            PaidScreen(
                onBottomButtonClick = { navController.navigate(HotelDestination.route) },
                navigateBack = { navController.navigateUp() })
        }
    }
}
