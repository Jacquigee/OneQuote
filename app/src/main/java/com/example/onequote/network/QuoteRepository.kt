package com.example.onequote.network

import com.example.onequote.network.models.NetworkQuote
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteService: QuoteService
) {
    suspend fun getQuoteOfTheDay(): NetworkQuote? {
        return quoteService.getQuoteOfTheDay()?.body()?.first()
    }
}