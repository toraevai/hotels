package com.example.hotels.ui.booking

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotels.R
import com.example.hotels.data.TouristInfo
import com.example.hotels.data.emptyTourist
import com.example.hotels.model.BookRoom
import com.example.hotels.navigation.NavigationDestination
import com.example.hotels.ui.parts.ErrorScreen
import com.example.hotels.ui.parts.HotelInfo
import com.example.hotels.ui.parts.HotelsBottomBarWithButton
import com.example.hotels.ui.parts.HotelsTopAppBar
import com.example.hotels.ui.parts.LoadingScreen
import com.example.hotels.ui.parts.PhoneNumberTransformation
import com.example.hotels.ui.theme.GrayLabel
import com.example.hotels.ui.theme.HotelsBlue
import com.example.hotels.ui.theme.HotelsDarkGray
import com.example.hotels.ui.theme.HotelsGray
import com.example.hotels.ui.theme.HotelsRed
import java.text.NumberFormat

object BookingDestination : NavigationDestination {
    override val route = "booking"
    override val titleRes = R.string.title_booking_screen
}

@Composable
fun BookingScreen(
    bookingUiState: BookingUiState,
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
    retryAction: () -> Unit
) {
    when (bookingUiState) {
        is BookingUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is BookingUiState.Success -> BookingRoomScreen(
            room = bookingUiState.bookRoom,
            userPhone = userPhone,
            userMail = userMail,
            touristsInfo = touristsInfo,
            onUserPhoneChange = onUserPhoneChange,
            onUserMailChange = onUserMailChange,
            emailChecked = emailChecked,
            checkEmail = checkEmail,
            addTourist = addTourist,
            onTouristNameChange = onTouristNameChange,
            onTouristSurnameChange = onTouristSurnameChange,
            onTouristBirthdayChange = onTouristBirthdayChange,
            onTouristCitizenshipChange = onTouristCitizenshipChange,
            onTouristPassNumChange = onTouristPassNumChange,
            onTouristPassEndDateChange = onTouristPassEndDateChange,
            conditionsChecked = conditionsChecked,
            onPaidClick = onPaidClick,
            navigateBack = navigateBack)
        is BookingUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingRoomScreen(
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
                title = stringResource(BookingDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        bottomBar = {
            HotelsBottomBarWithButton(
                text = stringResource(
                    R.string.pay_booking,
                    room.tourPrice + room.fuelCharge + room.serviceCharge,
                    NumberFormat.getCurrencyInstance().currency!!.symbol
                ),
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
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        HotelInfo(
                            hotelRating = room.rating,
                            hotelRatingName = room.ratingName,
                            hotelName = room.hotelName,
                            hotelAddress = room.hotelAddress
                        )
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
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        SingleBookingInfo(name = stringResource(R.string.departure), info = room.departure)
                        SingleBookingInfo(name = stringResource(R.string.arrival), info = room.arrivalCountry)
                        SingleBookingInfo(
                            name = stringResource(R.string.travel_dates),
                            info = "${room.tourDateStart}-${room.tourDateStop}"
                        )
                        SingleBookingInfo(
                            name = stringResource(R.string.number_of_nights),
                            info = room.numberOfNights.toString()
                        )
                        SingleBookingInfo(name = stringResource(R.string.hotel), info = room.hotelName)
                        SingleBookingInfo(name = stringResource(R.string.room), info = room.room)
                        SingleBookingInfo(name = stringResource(R.string.nutrition), info = room.nutrition)
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
                            text = stringResource(R.string.user_info),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        UserInfoSingleField(
                            label = stringResource(R.string.user_phone_number),
                            text = userPhone,
                            onValueChange = onUserPhoneChange,
                            isError = if (conditionsChecked) userPhone.length != 10 else false,
                            visualTransformation = PhoneNumberTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        EmailTextField(
                            userMail = userMail,
                            onUserMailChange = onUserMailChange,
                            isError = if (emailChecked) !userMail.contains(Regex("\\w+@\\w+.\\w{2,}")) else false,
                            checkEmail = checkEmail
                        )
                        Text(
                            text = stringResource(R.string.public_offer),
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
                            name = stringResource(R.string.tour_price),
                            info = "${room.tourPrice} ${NumberFormat.getCurrencyInstance().currency!!.symbol}"
                        )
                        SinglePriceInfo(
                            name = stringResource(R.string.fuel_charge),
                            info = "${room.fuelCharge} ${NumberFormat.getCurrencyInstance().currency!!.symbol}"
                        )
                        SinglePriceInfo(
                            name = stringResource(R.string.service_charge),
                            info = "${room.serviceCharge} ${NumberFormat.getCurrencyInstance().currency!!.symbol}"
                        )
                        SinglePriceInfo(
                            name = stringResource(R.string.payment),
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
    isError: Boolean,
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
        label = stringResource(R.string.email),
        text = userMail,
        onValueChange = onUserMailChange,
        isError = isError,
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
                        1 -> stringResource(R.string.first_tourist)
                        2 -> stringResource(R.string.second_tourist)
                        3 -> stringResource(R.string.third_tourist)
                        4 -> stringResource(R.string.fourth_tourist)
                        5 -> stringResource(R.string.fifth_tourist)
                        6 -> stringResource(R.string.sixth_tourist)
                        7 -> stringResource(R.string.seventh_tourist)
                        8 -> stringResource(R.string.eighth_tourist)
                        9 -> stringResource(R.string.ninth_tourist)
                        else -> touristNumber.toString()
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = if (isExpanded) R.drawable.expand_less else R.drawable.expand_more),
                    contentDescription = stringResource(R.string.add_tourist),
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(onClick = { isExpanded = !isExpanded }),
                    tint = HotelsBlue
                )
            }
            if (isExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))) {
                    UserInfoSingleField(
                        label = stringResource(R.string.tourist_name),
                        text = touristInfo.name.value,
                        onValueChange = onNameChange,
                        isError = if (conditionsChecked) touristInfo.name.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = stringResource(R.string.tourist_surname),
                        text = touristInfo.surname.value,
                        onValueChange = onSurnameChange,
                        isError = if (conditionsChecked) touristInfo.surname.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = stringResource(R.string.tourist_dirthday),
                        text = touristInfo.birthday.value,
                        onValueChange = onBirthdayChange,
                        isError = if (conditionsChecked) touristInfo.birthday.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = stringResource(R.string.tourist_citizenship),
                        text = touristInfo.citizenship.value,
                        onValueChange = onCitizenshipChange,
                        isError = if (conditionsChecked) touristInfo.citizenship.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = stringResource(R.string.tourist_pass_num),
                        text = touristInfo.internationalPassportNumber.value,
                        onValueChange = onPassportNumberChange,
                        isError = if (conditionsChecked) touristInfo.internationalPassportNumber.value.isEmpty() else false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UserInfoSingleField(
                        label = stringResource(R.string.tourist_pass_date),
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
                text = stringResource(R.string.add_tourist),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.add_box_32),
                contentDescription = stringResource(R.string.add_tourist),
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