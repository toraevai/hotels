package com.example.hotels.ui.parts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hotels.R

@Composable
fun HotelsBottomBarWithButton(text: String, onClick: () -> Unit) {
    BottomAppBar {
        BigButton(
            text = text,
            onClick = onClick,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        )
    }
}

@Preview
@Composable
fun HotelsBottomBarWithButtonPreview() {
    HotelsBottomBarWithButton(text = "К выбору номера", onClick = {})
}