package com.example.hotels.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hotels.R
import com.example.hotels.model.Hotel
import com.example.hotels.navigation.NavigationDestination
import com.example.hotels.ui.parts.BottomBarWithButton
import com.example.hotels.ui.parts.HotelsTopAppBar
import com.example.hotels.ui.parts.PhotoListScreen
import com.example.hotels.ui.parts.SmallTextWithImages
import com.example.hotels.ui.theme.BackDarkGray
import com.example.hotels.ui.theme.BackOrange
import com.example.hotels.ui.theme.Blue
import com.example.hotels.ui.theme.DarkGray
import com.example.hotels.ui.theme.Orange
import java.text.NumberFormat

object HotelScreen : NavigationDestination {
    override val route = "hotel"
    override val titleRes = "Отель"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HotelScreen(
    hotel: Hotel,
    navigateToRooms: () -> Unit
) {
    Scaffold(
        topBar = {
            HotelsTopAppBar(title = HotelScreen.titleRes, canNavigateBack = false)
        },
        bottomBar = {
            BottomBarWithButton(
                text = "К выбору номера",
                onClick = navigateToRooms
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(innerPadding),
            contentPadding = innerPadding
        ) {
            item { PhotosAndBasicData(hotel = hotel) }
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
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            PhotoListScreen(urls = hotel.imageUrls, modifier = Modifier.height(300.dp))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            SmallTextWithImages(
                startImage = R.drawable.star_rate_12,
                text = "${hotel.rating} ${hotel.ratingName}",
                textColor = Orange,
                backgroundColor = BackOrange,
                modifier = modifier
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Text(
                text = hotel.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Text(
                text = hotel.adress,
                color = Blue,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable { }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.headlineMedium.toSpanStyle()
                        ) { append("От ${hotel.minimalPrice} ${NumberFormat.getCurrencyInstance().currency!!.symbol} ") }
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                                .copy(color = DarkGray),
                        ) { append(hotel.priceForIt.lowercase()) }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailedHotelInfo(
    hotelDetailedInfo: List<String>,
    hotelDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = "Об отеле",
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
                        textColor = DarkGray,
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
                text = "Удобства",
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
                text = "Что включено",
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
                text = "Что не включено",
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
                text = "Самое необходимое",
                color = DarkGray,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.navigate_next_24),
            contentDescription = "Перейти к $text",
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