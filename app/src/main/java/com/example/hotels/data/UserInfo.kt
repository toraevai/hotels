package com.example.hotels.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class UserInfo(
    val phone: String,
    val mail: String,
    val tourists: SnapshotStateList<TouristInfo> = mutableStateListOf(
        TouristInfo(
            name = mutableStateOf(""),
            surname = mutableStateOf(""),
            birthday = mutableStateOf(""),
            citizenship = mutableStateOf(""),
            internationalPassportNumber = mutableStateOf(""),
            internationalPassportEndDate = mutableStateOf("")
        )
    )
)

val emptyTourist = TouristInfo(
    name = mutableStateOf(""),
    surname = mutableStateOf(""),
    birthday = mutableStateOf(""),
    citizenship = mutableStateOf(""),
    internationalPassportNumber = mutableStateOf(""),
    internationalPassportEndDate = mutableStateOf("")
)

data class TouristInfo(
    var name: MutableState<String>,
    var surname: MutableState<String>,
    var birthday: MutableState<String>,
    var citizenship: MutableState<String>,
    var internationalPassportNumber: MutableState<String>,
    var internationalPassportEndDate: MutableState<String>
)