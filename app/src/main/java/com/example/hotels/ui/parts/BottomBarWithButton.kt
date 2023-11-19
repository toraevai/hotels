package com.example.hotels.ui.parts

import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomBarWithButton(text: String, onClick: () -> Unit) {
    BottomAppBar {
        BigButton(text = text, onClick = onClick)
    }
}

@Preview
@Composable
fun BottomBarWithButtonPreview() {
    BottomBarWithButton(text = "К выбору номера", onClick = {})
}