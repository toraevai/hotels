package com.example.hotels.ui.hotel

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotels.R
import com.example.hotels.model.Hotel
import com.example.hotels.navigation.NavigationDestination
import com.example.hotels.ui.parts.ErrorScreen
import com.example.hotels.ui.parts.LoadingScreen
import com.example.hotels.ui.parts.HotelInfo
import com.example.hotels.ui.parts.HotelsBottomBarWithButton
import com.example.hotels.ui.parts.HotelsTopAppBar
import com.example.hotels.ui.parts.PhotoListScreen
import com.example.hotels.ui.parts.SmallTextWithImages
import com.example.hotels.ui.theme.BackDarkGray
import com.example.hotels.ui.theme.HotelsDarkGray
import com.example.hotels.ui.theme.HotelsGray
import java.text.NumberFormat

object HotelDestination : NavigationDestination {
    override val route = "hotel"
    override val titleRes = R.string.title_hotel_screen
}

@Composable
fun HotelScreen(
    hotelUiState: HotelUiState,
    navigateToRooms: () -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (hotelUiState) {
        is HotelUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is HotelUiState.Success -> HotelInfoScreen(
            hotel = hotelUiState.hotel,
            navigateToRooms = navigateToRooms,
            modifier = modifier.fillMaxSize()
        )

        is HotelUiState.Error -> ErrorScreen(retryAction = retryAction)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelInfoScreen(
    hotel: Hotel,
    navigateToRooms: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            HotelsTopAppBar(
                title = stringResource(HotelDestination.titleRes),
                canNavigateBack = false
            )
        },
        bottomBar = {
            HotelsBottomBarWithButton(
                text = stringResource(R.string.hotel_screen_go_to_hotel_rooms),
                onClick = navigateToRooms
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.background(HotelsGray),
            contentPadding = innerPadding
        ) {
            item { PhotosAndBasicData(hotel = hotel, modifier = Modifier.fillMaxWidth()) }
            item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small))) }
            item {
                DetailedHotelInfo(
                    hotel.aboutTheHotel.peculiarities,
                    hotel.aboutTheHotel.description,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item { Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium_12))) }
        }
    }
}

@Composable
fun PhotosAndBasicData(hotel: Hotel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            PhotoListScreen(urls = hotel.imageUrls, modifier = Modifier.height(300.dp))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            HotelInfo(
                hotelRating = hotel.rating,
                hotelRatingName = hotel.ratingName,
                hotelName = hotel.name,
                hotelAddress = hotel.address
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.headlineMedium.toSpanStyle()
                        ) {
                            append(
                                stringResource(
                                    R.string.hotel_screen_min_price,
                                    hotel.minimalPrice,
                                    NumberFormat.getCurrencyInstance().currency!!.symbol
                                )
                            )
                        }
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                                .copy(color = HotelsDarkGray),
                        ) { append(hotel.priceForIt.lowercase()) }
                    }
                )
            }
        }
    }
}

@Composable
fun DetailedHotelInfo(
    hotelDetailedInfo: List<String>,
    hotelDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.hotel_screen_about_hotel),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Adaptive(29.dp),
                modifier = Modifier.height(66.dp),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                horizontalItemSpacing = dimensionResource(id = R.dimen.padding_small)
            ) {
                items(hotelDetailedInfo) { info ->
                    SmallTextWithImages(
                        text = info,
                        textColor = HotelsDarkGray,
                        backgroundColor = BackDarkGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium_12)))
            Text(
                text = hotelDescription,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            AdvancedFeatures(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun AdvancedFeatures(modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = BackDarkGray)
    ) {
        Column(modifier = modifier.padding(all = 10.dp)) {
            HotelFeature(
                icon = R.drawable.emoji_happy,
                text = stringResource(R.string.hotel_screen_amenities),
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                modifier = Modifier.padding(
                    start = (29.dp + dimensionResource(id = R.dimen.padding_medium_12)),
                    end = 5.dp
                )
            )
            HotelFeature(
                icon = R.drawable.tick,
                text = stringResource(R.string.hotel_screen_what_included),
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                modifier = Modifier.padding(
                    start = (29.dp + dimensionResource(id = R.dimen.padding_medium_12)),
                    end = 5.dp
                )
            )
            HotelFeature(
                icon = R.drawable.close,
                text = stringResource(R.string.hotel_screen_what_is_not_included),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun HotelFeature(
    @DrawableRes icon: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentHeight()
            .padding(all = 5.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium_12)))
        Column {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.hotel_screen_essentials),
                color = HotelsDarkGray,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.navigate_next_24),
            contentDescription = stringResource(R.string.hotel_screen_go_to, text),
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HotelScreenPreview() {
    val hotelInfo = listOf(
        "Бесплатный Wifi на всей территории отеля",
        "1 км до пляжа",
        "Бесплатный фитнес-клуб",
        "20 км до аэропорта"
    )
    DetailedHotelInfo(
        hotelDetailedInfo = hotelInfo,
        hotelDescription = "Отель VIP-класса с собственными гольф полями. Высокий уровнь сервиса. Рекомендуем для респектабельного отдыха. Отель принимает гостей от 18 лет!"
    )
}