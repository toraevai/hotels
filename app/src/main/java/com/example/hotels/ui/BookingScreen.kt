package com.example.hotels.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotels.R
import com.example.hotels.data.TouristInfo
import com.example.hotels.data.emptyTourist
import com.example.hotels.model.BookRoom
import com.example.hotels.navigation.NavigationDestination
import com.example.hotels.ui.parts.HotelInfo
import com.example.hotels.ui.parts.HotelsBottomBarWithButton
import com.example.hotels.ui.parts.HotelsTopAppBar
import com.example.hotels.ui.parts.PhoneNumberTransformation
import com.example.hotels.ui.theme.GrayLabel
import com.example.hotels.ui.theme.HotelsBlue
import com.example.hotels.ui.theme.HotelsDarkGray
import com.example.hotels.ui.theme.HotelsGray
import com.example.hotels.ui.theme.HotelsRed
import java.text.NumberFormat

object BookingDestination : NavigationDestination {
    override val route = "booking"
    override val titleRes = "Бронирование"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    room: BookRoom,
    userPhone: String,
    userMail: String,
    touristsInfo: List<TouristInfo>,
    onUserPhoneChange: (String) -> Unit,
    onUserMailChange: (String) -> Unit,
    emailChecked: Boolean,
    checkEmail: () -> Unit,
    addTourist: () -> Unit,
    onTouristNameChange: (Int, String) -> Unit,
    onTouristSurnameChange: (Int, String) -> Unit,
    onTouristBirthdayChange: (Int, String) -> Unit,
    onTouristCitizenshipChange: (Int, String) -> Unit,
    onTouristPassNumChange: (Int, String) -> Unit,
    onTouristPassEndDateChange: (Int, String) -> Unit,
    conditionsChecked: Boolean,
    onPaidClick: () -> Unit,
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
                text = "Оплатить ${room.tourPrice + room.fuelCharge + room.serviceCharge} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                onClick = onPaidClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            item {
                HotelInfo(
                    hotelRating = room.rating,
                    hotelRatingName = room.ratingName,
                    hotelName = room.hotelName,
                    hotelAddress = room.hotelAddress
                )
            }
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                        modifier = Modifier.padding(all = 8.dp)
                    ) {
                        SingleBookingInfo(name = "Вылет из", info = room.departure)
                        SingleBookingInfo(name = "Страна, город", info = room.arrivalCountry)
                        SingleBookingInfo(
                            name = "Даты",
                            info = "${room.tourDateStart}-${room.tourDateStop}"
                        )
                        SingleBookingInfo(
                            name = "Кол-во ночей",
                            info = room.numberOfNights.toString()
                        )
                        SingleBookingInfo(name = "Отель", info = room.hotelName)
                        SingleBookingInfo(name = "Номер", info = room.room)
                        SingleBookingInfo(name = "Питание", info = room.nutrition)
                    }
                }
            }
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        Text(
                            text = "Информация о покупателе",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        UserInfoSingleField(
                            label = "Номер телефона",
                            text = userPhone,
                            onValueChange = onUserPhoneChange,
                            isError = if (conditionsChecked) userPhone.isEmpty() else false,
                            visualTransformation = PhoneNumberTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        EmailTextField(
                            userMail = userMail,
                            onUserMailChange = onUserMailChange,
                            emailChecked = emailChecked,
                            checkEmail = checkEmail
                        )
                        Text(
                            text = "Эти данные никому не передаются. После оплаты мы вышлем чек на указанные вами номер и почту",
                            style = MaterialTheme.typography.bodySmall,
                            color = HotelsDarkGray
                        )
                    }
                }
            }
            itemsIndexed(touristsInfo) { index, touristInfo ->
                Tourist(
                    touristNumber = index + 1,
                    touristInfo = touristInfo,
                    onNameChange = { onTouristNameChange(index, it) },
                    onSurnameChange = { onTouristSurnameChange(index, it) },
                    onBirthdayChange = { onTouristBirthdayChange(index, it) },
                    onCitizenshipChange = { onTouristCitizenshipChange(index, it) },
                    onPassportNumberChange = { onTouristPassNumChange(index, it) },
                    onPassportEndDateChange = { onTouristPassEndDateChange(index, it) },
                    conditionsChecked = conditionsChecked
                )
            }
            item { AddTourist(onClick = addTourist) }
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                        modifier = Modifier.padding(all = 8.dp)
                    ) {
                        SinglePriceInfo(
                            name = "Тур",
                            info = "${room.tourPrice} ${NumberFormat.getCurrencyInstance().currency!!.symbol}"
                        )
                        SinglePriceInfo(
                            name = "Топлинвый сбор",
                            info = "${room.fuelCharge} ${NumberFormat.getCurrencyInstance().currency!!.symbol}"
                        )
                        SinglePriceInfo(
                            name = "Сервисный сбор",
                            info = "${room.serviceCharge} ${NumberFormat.getCurrencyInstance().currency!!.symbol}"
                        )
                        SinglePriceInfo(
                            name = "К оплате",
                            info = "${room.tourPrice + room.fuelCharge + room.serviceCharge} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                            secondTextColor = HotelsBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmailTextField(
    userMail: String,
    onUserMailChange: (String) -> Unit,
    emailChecked: Boolean,
    checkEmail: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is FocusInteraction.Unfocus) {
                checkEmail()
            }
        }
    }
    UserInfoSingleField(
        label = "Почта",
        text = userMail,
        onValueChange = onUserMailChange,
        isError = if (emailChecked) !userMail.contains(Regex("\\w+@\\w+.\\w{2,}")) else false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        interactionSource = interactionSource,
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun SingleBookingInfo(name: String, info: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = HotelsDarkGray
        )
        Text(
            text = info, style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 150.dp)
        )
    }
}

@Composable
fun SinglePriceInfo(
    name: String,
    info: String,
    secondTextColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = HotelsDarkGray
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = info, style = MaterialTheme.typography.bodyMedium, color = secondTextColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoSingleField(
    label: String,
    text: String,
    placeholder: String = "",
    isError: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    modifier: Modifier = Modifier
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.height(52.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(
                text = label,
                color = GrayLabel
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(10.dp),
        colors = if (isError) {
            TextFieldDefaults.textFieldColors(
                containerColor = HotelsRed,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        } else {
            TextFieldDefaults.textFieldColors(
                containerColor = HotelsGray,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        }
    )
}

@Composable
fun Tourist(
    touristNumber: Int,
    touristInfo: TouristInfo,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onBirthdayChange: (String) -> Unit,
    onCitizenshipChange: (String) -> Unit,
    onPassportNumberChange: (String) -> Unit,
    onPassportEndDateChange: (String) -> Unit,
    conditionsChecked: Boolean
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Row {
                Text(
                    text = when (touristNumber) {
                        1 -> "Первый турист"
                        2 -> "Второй турист"
                        3 -> "Третий турист"
                        4 -> "Четвертый турист"
                        5 -> "Пятый турист"
                        6 -> "Шестой турист"
                        7 -> "Седьмой турист"
                        8 -> "Восьмой турист"
                        9 -> "Девятый турист"
                        else -> touristNumber.toString()
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = if (isExpanded) R.drawable.expand_less else R.drawable.expand_more),
                    contentDescription = "Добавить туриста",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(onClick = { isExpanded = !isExpanded }),
                    tint = HotelsBlue
                )
            }
            if (isExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))) {
                    UserInfoSingleField(
                        label = "Имя",
                        text = touristInfo.name.value,
                        onValueChange = onNameChange,
                        isError = if (conditionsChecked) touristInfo.name.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = "Фамилия",
                        text = touristInfo.surname.value,
                        onValueChange = onSurnameChange,
                        isError = if (conditionsChecked) touristInfo.surname.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = "Дата рождения",
                        text = touristInfo.birthday.value,
                        onValueChange = onBirthdayChange,
                        isError = if (conditionsChecked) touristInfo.birthday.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = "Гражданство",
                        text = touristInfo.citizenship.value,
                        onValueChange = onCitizenshipChange,
                        isError = if (conditionsChecked) touristInfo.citizenship.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = "Номер загранпаспорта",
                        text = touristInfo.internationalPassportNumber.value,
                        onValueChange = onPassportNumberChange,
                        isError = if (conditionsChecked) touristInfo.internationalPassportNumber.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = "Срок действия загранпаспорта",
                        text = touristInfo.internationalPassportEndDate.value,
                        onValueChange = onPassportEndDateChange,
                        isError = if (conditionsChecked) touristInfo.internationalPassportEndDate.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun AddTourist(onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))) {
            Text(
                text = "Добавить туриста",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.add_box_32),
                contentDescription = "Добавить туриста",
                modifier = Modifier
                    .size(32.dp)
                    .clickable(onClick = onClick),
                tint = HotelsBlue
            )
        }
    }
}

@Preview
@Composable
fun UserInfoSingleFieldPreview() {
    Tourist(
        touristNumber = 1,
        touristInfo = emptyTourist,
        onNameChange = {},
        onSurnameChange = {},
        onBirthdayChange = {},
        onCitizenshipChange = {},
        onPassportNumberChange = {},
        onPassportEndDateChange = {},
        conditionsChecked = false
    )
}