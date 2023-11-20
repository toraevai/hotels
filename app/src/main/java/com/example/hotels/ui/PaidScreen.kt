package com.example.hotels.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotels.R
import com.example.hotels.navigation.NavigationDestination
import com.example.hotels.ui.parts.HotelsBottomBarWithButton
import com.example.hotels.ui.parts.HotelsTopAppBar
import com.example.hotels.ui.theme.HotelsDarkGray
import com.example.hotels.ui.theme.HotelsTheme
import kotlin.random.Random

object PaidDestination : NavigationDestination {
    override val route = "paid"
    override val titleRes = "Заказ оплачен"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaidScreen(
    onBottomButtonClick: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HotelsTopAppBar(
                title = BookingDestination.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        bottomBar = {
            HotelsBottomBarWithButton(
                text = "Супер!",
                onClick = onBottomButtonClick
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.size(94.dp),
                shape = RoundedCornerShape(47.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.party_popper),
                        contentDescription = "Поздравляем, Вы оформили путевку",
                        modifier = Modifier.size(44.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Ваш заказ принят в работу", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Подтверждение заказа №${
                    Random.nextInt(
                        0,
                        999999
                    )
                } может занять некоторое время (от 1 часа до суток). Как только мы получим ответ от туроператора, вам на почту придет уведомление.",
                modifier = Modifier.padding(horizontal = 23.dp),
                color = HotelsDarkGray,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PaidScreenPreview() {
    HotelsTheme {
        PaidScreen(
            onBottomButtonClick = {},
            navigateBack = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}