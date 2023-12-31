package com.example.onequote

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    fun getNext(): SortOrder{
        return when (this){
            is Shortest -> Longest
            is Longest -> Author
            is Author -> Shortest
        }
    }

    fun getPrevious(): SortOrder{
        return when (this){
            is Shortest -> Author
            is Longest -> Shortest
            is Author -> Longest
        }
    }
}

@Composable
private fun SortComponent(sortOrder: SortOrder, onNext: () -> Unit, onPrevious: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .border(
                    width = 1.dp,
                    color = Color.Cyan,
                    shape = MaterialTheme.shapes.large
                )
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(shape = MaterialTheme.shapes.large)
                .clickable { onPrevious() }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = Color.Cyan,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = "Sort order: ${sortOrder.displayName}",
            color = Color.Cyan,
            modifier = Modifier
                .border(1.dp, color = Color.Cyan, shape = MaterialTheme.shapes.large)
                .padding(horizontal = 32.dp, vertical = 12.dp)
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .border(
                    width = 1.dp,
                    color = Color.Cyan,
                    shape = MaterialTheme.shapes.large
                )
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(shape = MaterialTheme.shapes.large)
                .clickable { onNext() }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Forward",
                tint = Color.Cyan,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AllQuotesDisplay(
    quotes: List<AppState.Quote>,
    onFavoriteClicked: (AppState.Quote) -> Unit
) {
    var currentSortOrder by remember { mutableStateOf<SortOrder>(SortOrder.Shortest) }
    val sortedQuotes by remember {
        derivedStateOf {
            when (currentSortOrder) {
                SortOrder.Author -> quotes.sortedBy { it.author }
                SortOrder.Longest -> quotes.sortedByDescending { it.displayText.length }
                SortOrder.Shortest -> quotes.sortedBy { it.displayText.length }
            }
        }
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 48.dp)
    ) {
        item {
            SortComponent(
                sortOrder = currentSortOrder,
                onNext = { currentSortOrder = currentSortOrder.getNext() },
                onPrevious = { currentSortOrder = currentSortOrder.getPrevious() }
            )
        }
        items(items = quotes, key = { it.hashCode() }) {
            SingleQuoteListItem(quote = it, onFavoriteClicked = { onFavoriteClicked(it) }, modifier = Modifier.animateItemPlacement())
        }
    }
}