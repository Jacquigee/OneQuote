package com.example.onequote.domain.mappers

import com.example.onequote.AppState
import com.example.onequote.network.models.NetworkQuote
import javax.inject.Inject

class QuoteMapper @Inject constructor(){
    fun buildFrom(networkQuote: NetworkQuote): AppState.Quote {
        return AppState.Quote(
            displayText = networkQuote.q,
            author = networkQuote.a,
            isFavorite = false
        )
    }
}