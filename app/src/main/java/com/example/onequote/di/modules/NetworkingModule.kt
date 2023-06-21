package com.example.onequote.di.modules

import com.example.onequote.network.QuoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun providesQuoteService(retrofit: Retrofit): QuoteService {
        return retrofit.create(QuoteService::class.java)
    }
}