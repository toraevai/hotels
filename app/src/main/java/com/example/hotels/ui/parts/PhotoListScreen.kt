package com.example.hotels.ui.parts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun PhotoListScreen(urls: List<String>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(urls) { url ->
            PhotoCard(
                url = url,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@Composable
fun PhotoCard(url: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(url)
                .crossfade(true).build(),
            contentDescription = "Фото отеля",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}