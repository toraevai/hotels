package com.example.hotels.ui.parts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.hotels.R
import com.example.hotels.ui.theme.Blue

@Composable
fun BottomBarWithButton(text: String, onClick: () -> Unit) {
    BottomAppBar {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            shape = MaterialTheme.shapes.large,
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_medium_12)
                )
        )
        {
            Text(
                text = text,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun BottomBarWithButtonPreview() {
    BottomBarWithButton(text = "К выбору номера", onClick = {})
}