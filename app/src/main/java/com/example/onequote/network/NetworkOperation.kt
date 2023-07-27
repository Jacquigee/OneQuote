package com.example.onequote.network

import androidx.compose.runtime.Composable

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


    //functional programming
    @Composable
    fun onSuccess(action: @Composable (data: T) -> Unit): NetworkOperation<T> {
        if (this is Success) {action( this.data )}
        return this
    }

    @Composable
    fun onFailure(action: @Composable (errorReason: String) -> Unit): NetworkOperation<T>{
        if (this is Failure) {action(this.reason)}
        return this
    }

    @Composable
    fun onLoading(action: @Composable (placeholder: T?) -> Unit): NetworkOperation<T>{
        if (this is Loading) {action(this.placeholder)}
        return this
    }
}