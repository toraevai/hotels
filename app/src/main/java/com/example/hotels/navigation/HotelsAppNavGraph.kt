package com.example.hotels.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hotels.model.Hotel
import com.example.hotels.model.Room
import com.example.hotels.ui.HotelRoomsScreen
import com.example.hotels.ui.HotelScreen

@Composable
fun HotelsAppNavHost(
    hotel: Hotel,
    hotelRooms: List<Room>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = HotelScreen.route, modifier = modifier
    ) {
        composable(route = HotelScreen.route) {
            HotelScreen(
                hotel = hotel,
                navigateToRooms = { navController.navigate(HotelRoomsScreen.route) }
            )
        }
        composable(route = HotelRoomsScreen.route) {
            HotelRoomsScreen(
                hotel = hotel,
                hotelRooms = hotelRooms,
                onClick = { navController.navigate() })
        }
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem =
                {
                    navController.navigate("${ItemEditDestination.route}/$it")
                },
                navigateBack = { navController.navigateUp() })
        }
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}
