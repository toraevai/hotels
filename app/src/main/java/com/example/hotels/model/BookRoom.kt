package com.example.hotels.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookRoom(
    val id: Int,
    @SerialName("hotel_name")
    val hotelName: String,
    @SerialName("hotel_adress")
    val hotelAddress: String,
    @SerialName("horating")
    val rating: Int,
    @SerialName("rating_name")
    val ratingName: String,
    val departure: String,
    @SerialName("arrival_country")
    val arrivalCountry: String,
    @SerialName("tour_date_start")
    val tourDateStart: String,
    @SerialName("tour_date_stop")
    val tourDateStop: String,
    @SerialName("number_of_nights")
    val numberOfNights: Int,
    val room: String,
    val nutrition: String,
    @SerialName("tour_price")
    val tourPrice: Int,
    @SerialName("fuel_charge")
    val fuelCharge: Int,
    @SerialName("service_charge")
    val serviceCharge: Int
)