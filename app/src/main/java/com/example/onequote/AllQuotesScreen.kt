package com.example.onequote

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.onequote.components.LoadingComponent
import com.example.onequote.components.SingleQuoteListItem
import com.example.onequote.network.NetworkOperation

/**
 * PROJECT NAME: One Quote
 * USER        : jacqui
 * EMAIL       : gitaujaquiline@gmail.com
 * DATE        : Aug, 8/2/23
 * TIME        : 11:49 PM
 */
@Composable
fun AllQuotesScreen(
    allQuotesOperation: NetworkOperation<List<AppState.Quote>>,
    onFavoriteClicked: (AppState.Quote) -> Unit
) {
    allQuotesOperation.onLoading {
        LoadingComponent()
    }.onSuccess {
        AllQuotesDisplay(quotes = it, onFavoriteClicked = onFavoriteClicked)
    }.onFailure {

    }
}

sealed class SortOrder(val displayName: String){
    object Shortest: SortOrder("Shortest")
    object Longest: SortOrder("Longest")
    object Author: SortOrder("Author")
}

@Composable
private fun SortComponent(sortOrder: SortOrder, onNext: () -> Unit, onPrevious: () -> Unit) {
    Row(horizontalArrangement = Arrangement.Center) {
        Text(
            text = sortOrder.displayName,
            color = Color.Cyan,
            modifier = Modifier
                .border(1.dp, color = Color.Cyan)
                .padding(horizontal = 32.dp, vertical = 12.dp)
        )
    }
}

@Composable
private fun AllQuotesDisplay(
    quotes: List<AppState.Quote>,
    onFavoriteClicked: (AppState.Quote) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 48.dp)
    ) {
        items(items = quotes, key = { it.hashCode() }) {
            SingleQuoteListItem(quote = it, onFavoriteClicked = { onFavoriteClicked(it) })
        }
    }
}