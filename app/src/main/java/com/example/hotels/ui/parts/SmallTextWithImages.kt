package com.example.hotels.ui.parts

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotels.R
import com.example.hotels.ui.theme.BackOrange
import com.example.hotels.ui.theme.Orange

@Composable
fun SmallTextWithImages(
    @DrawableRes startImage: Int? = null,
    text: String,
    @DrawableRes endImage: Int? = null,
    textColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor, contentColor = textColor
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier.wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            if (startImage != null){
                Image(
                    painter = painterResource(startImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(textColor),
                    modifier = Modifier.size(15.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
            if (endImage != null){
                Image(
                    painter = painterResource(endImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(textColor),
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SmallTextWithImagesPreview() {
    SmallTextWithImages(
        startImage = R.drawable.star_rate_12,
        text = "5 Превосходно",
        textColor = Orange,
        backgroundColor = BackOrange
    )
}