package com.example.hotels.ui.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.hotels.R
import com.example.hotels.model.Hotel
import com.example.hotels.ui.theme.BackOrange
import com.example.hotels.ui.theme.Blue
import com.example.hotels.ui.theme.Orange

@Composable
fun HotelInfo(hotelRating: Int, hotelRatingName: String, hotelName: String, hotelAddress: String, modifier: Modifier = Modifier) {
    SmallTextWithImages(
        startImage = R.drawable.star_rate_12,
        text = "$hotelRating $hotelRatingName",
        textColor = Orange,
        backgroundColor = BackOrange,
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
    Text(
        text = hotelName,
        style = MaterialTheme.typography.headlineSmall
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
    Text(
        text = hotelAddress,
        color = Blue,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.clickable { }
    )
}