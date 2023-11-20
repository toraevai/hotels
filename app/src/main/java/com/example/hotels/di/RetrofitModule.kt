package com.example.hotels.di

import com.example.hotels.data.HotelRepository
import com.example.hotels.network.HotelsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    val baseUrl = "https://run.mocky.io/v3/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideHotelsApiService(retrofit: Retrofit): HotelsApiService =
        retrofit.create(HotelsApiService::class.java)

    @Provides
    @Singleton
    fun provideHotelRepository(hotelsApiService: HotelsApiService): HotelRepository =
        HotelRepository(hotelsApiService)
}