package com.example.onequote.network

import com.example.onequote.AppState
import com.example.onequote.domain.mappers.QuoteMapper
import com.example.onequote.network.models.NetworkQuote
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteService: QuoteService,
    private val quoteMapper: QuoteMapper
) {
    suspend fun getQuoteOfTheDay(): AppState.Quote? {
        val networkQuote: NetworkQuote? = quoteService.getQuoteOfTheDay()?.body()?.first()
        return networkQuote?.let { quoteMapper.buildFrom(it)}
    }
}