package com.example.hotels.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.hotels.R
import com.example.hotels.model.Hotel
import com.example.hotels.model.Room
import com.example.hotels.navigation.NavigationDestination
import com.example.hotels.ui.parts.BigButton
import com.example.hotels.ui.parts.HotelsTopAppBar
import com.example.hotels.ui.parts.PhotoListScreen
import com.example.hotels.ui.parts.SmallTextWithImages
import com.example.hotels.ui.theme.BackBlue
import com.example.hotels.ui.theme.BackDarkGray
import com.example.hotels.ui.theme.Blue
import com.example.hotels.ui.theme.DarkGray
import java.text.NumberFormat

object HotelRoomsScreen : NavigationDestination {
    override val route = "hotel_rooms"
    override val titleRes = "Комнаты"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelRoomsScreen(hotel: Hotel, hotelRooms: List<Room>, onClick: () -> Unit) {
    Scaffold(
        topBar = {
            HotelsTopAppBar(title = hotel.name, canNavigateBack = true)
        }
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                .padding(paddingValues)
        ) {
            item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small))) }
            items(hotelRooms) { room ->
                HotelRoom(room = room, onClick = onClick)
            }
        }
    }
}

@Composable
fun HotelRoom(
    room: Room,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            PhotoListScreen(urls = room.imageUrls, modifier = Modifier.height(300.dp))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Text(
                text = room.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Adaptive(29.dp),
                modifier = Modifier.height(66.dp),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                horizontalItemSpacing = dimensionResource(id = R.dimen.padding_small)
            ) {
                items(room.peculiarities) { peculiarities ->
                    SmallTextWithImages(
                        text = peculiarities,
                        textColor = DarkGray,
                        backgroundColor = BackDarkGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            SmallTextWithImages(
                text = "Подробнее о номере",
                endImage = R.drawable.navigate_next_24,
                textColor = Blue,
                backgroundColor = BackBlue
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.headlineMedium.toSpanStyle()
                        ) { append("${room.price} ${NumberFormat.getCurrencyInstance().currency!!.symbol} ") }
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                                .copy(color = DarkGray),
                        ) { append(room.pricePer.lowercase()) }
                    }
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            BigButton(text = "Выбрать номер", onClick = onClick)
        }
    }
}