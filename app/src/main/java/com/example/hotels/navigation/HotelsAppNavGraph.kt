package com.example.hotels.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hotels.model.BookRoom
import com.example.hotels.model.Hotel
import com.example.hotels.model.Room
import com.example.hotels.ui.BookingDestination
import com.example.hotels.ui.BookingScreen
import com.example.hotels.ui.HotelRoomsScreen
import com.example.hotels.ui.HotelRoomsDestination
import com.example.hotels.ui.HotelScreen
import com.example.hotels.ui.HotelDestination
import com.example.hotels.ui.HotelsViewModel
import com.example.hotels.ui.PaidDestination
import com.example.hotels.ui.PaidScreen

@Composable
fun HotelsAppNavHost(
    hotelsViewModel: HotelsViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val hotel = hotelsViewModel.hotel
    val hotelRooms = hotelsViewModel.listOfRooms
    val room = hotelsViewModel.booking
    val user = hotelsViewModel.userInfo.collectAsState().value
    NavHost(
        navController = navController,
        startDestination = HotelDestination.route,
        modifier = modifier
    ) {
        composable(route = HotelDestination.route) {
            HotelScreen(
                hotel = hotel,
                navigateToRooms = { navController.navigate(HotelRoomsDestination.route) }
            )
        }
        composable(route = HotelRoomsDestination.route) {
            HotelRoomsScreen(
                hotel = hotel,
                hotelRooms = hotelRooms,
                onClick = { navController.navigate(BookingDestination.route) })
        }
        composable(route = BookingDestination.route) {
            BookingScreen(
                room = room,
                userPhone = user.phone,
                userMail = user.mail,
                touristsInfo = user.tourists,
                onUserPhoneChange = { hotelsViewModel.changePhone(it) },
                onUserMailChange = { hotelsViewModel.changeMail(it) },
                addTourist = { hotelsViewModel.addTourist() },
                onTouristNameChange = { index, name ->
                    hotelsViewModel.changeTouristName(index, name)
                },
                onTouristSurnameChange = { index, surname ->
                    hotelsViewModel.changeTouristSurname(index, surname)
                },
                onTouristBirthdayChange = { index, birthday ->
                    hotelsViewModel.changeTouristBirthday(index, birthday)
                },
                onTouristCitizenshipChange = { index, citizenship ->
                    hotelsViewModel.changeTouristCitizenship(index, citizenship)
                },
                onTouristPassNumChange = { index, passNumber ->
                    hotelsViewModel.changeTouristPassNumber(index, passNumber)
                },
                onTouristPassEndDateChange = { index, endDate ->
                    hotelsViewModel.changeTouristPassEndDate(index, endDate)
                },
                onPaidClick = { navController.navigate(PaidDestination.route) }
            )
        }
        composable(
            route = PaidDestination.route
        ) {
            PaidScreen(onBottomButtonClick = { navController.navigate(HotelDestination.route) })
        }
    }
}
