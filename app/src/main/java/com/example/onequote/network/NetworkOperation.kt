package com.example.onequote.network

import androidx.compose.ui.text.Placeholder

/**
 * PROJECT NAME: One Quote
 * USER        : jacqui
 * EMAIL       : gitaujaquiine@gmail.com
 * DATE        : Jul, 7/26/23
 * TIME        : 11:26 PM
 */
sealed interface NetworkOperation<T>{
    data class Loading<T>(val placeholder: T? = null): NetworkOperation<T>
    data class Success<T>(val data: T): NetworkOperation<T>
    data class Failure<T>(val reason: String): NetworkOperation<T>
}