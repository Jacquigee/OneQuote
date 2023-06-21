package com.example.onequote.network

import com.example.onequote.network.models.NetworkQuote
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {
    @GET("today")
    suspend fun getQuoteOfTheDay(): Response<List<NetworkQuote>>
}