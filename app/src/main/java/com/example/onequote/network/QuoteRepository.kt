package com.example.onequote.network

import com.example.onequote.AppState
import com.example.onequote.domain.mappers.QuoteMapper
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteService: QuoteService,
    private val quoteMapper: QuoteMapper
) {
    suspend fun getQuoteOfTheDay(): NetworkOperation<AppState.Quote> {
        return quoteService.getQuoteOfTheDay().run {
            if (isSuccessful){
                NetworkOperation.Success(data = quoteMapper.buildFrom(body()!!.first()))
            } else {
                NetworkOperation.Failure(reason = "Unable to fetch quote of the day")
            }
        }
    }

    suspend fun getAllQuotes(): NetworkOperation<List<AppState.Quote>>{
        return quoteService.getAllQuotes().run {
            if (isSuccessful){
                NetworkOperation.Success(data = body()!!.map { quoteMapper.buildFrom(it) })
            } else {
                NetworkOperation.Failure(reason = "Unable to fetch quote of the day")
            }
        }
    }
}