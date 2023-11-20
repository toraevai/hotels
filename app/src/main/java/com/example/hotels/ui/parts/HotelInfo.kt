package com.example.hotels.ui.parts

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hotels.R
import com.example.hotels.ui.theme.BackOrange
import com.example.hotels.ui.theme.HotelsBlue
import com.example.hotels.ui.theme.HotelsOrange

@Composable
fun HotelInfo(
    hotelRating: Int,
    hotelRatingName: String,
    hotelName: String,
    hotelAddress: String,
    modifier: Modifier = Modifier
) {
    SmallTextWithImages(
        startImage = R.drawable.star_rate_12,
        text = "$hotelRating $hotelRatingName",
        textColor = HotelsOrange,
        backgroundColor = BackOrange,
        modifier = modifier
    )
    Text(
        text = hotelName,
        style = MaterialTheme.typography.headlineSmall
    )
    Text(
        text = hotelAddress,
        color = HotelsBlue,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.clickable { }
    )
}