package com.example.onequote

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onequote.network.NetworkOperation
import com.example.onequote.ui.theme.Purple40


@Composable
fun DailyQuoteScreen(
    networkOperation: NetworkOperation<AppState.Quote>,
    onFavouriteClicked: (AppState.Quote) -> Unit,
    onRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        when (networkOperation) {
            is NetworkOperation.Failure -> {}
            is NetworkOperation.Loading -> {}
            is NetworkOperation.Success -> {}
        }
        networkOperation.onLoading { placeholderQuote: AppState.Quote? ->
            DailyQuoteContent(
                quoteText = placeholderQuote?.displayText ?: "Loading",
                quoteAuthor = placeholderQuote?.author ?: "The Valiant Dev",
                isFavourite = placeholderQuote?.isFavorite ?: false,
                onClick = {}
            )
        }.onSuccess { quote ->
            DailyQuoteContent(
                quoteText = quote.displayText,
                quoteAuthor = quote.author,
                isFavourite = quote.isFavorite,
                onClick = { onFavouriteClicked(quote) }
            )
        }.onFailure { errorReason ->
            DailyQuoteContent(
                quoteText = errorReason,
                quoteAuthor = "The Valiant Dev",
                isFavourite = false,
                onClick = { })
        }

        val backgroundColor = animateColorAsState(
            targetValue = when (networkOperation) {
                is NetworkOperation.Failure -> Color.Red
                is NetworkOperation.Loading -> Color.Yellow
                is NetworkOperation.Success -> Purple40
            }
        )
        Text(
            text = when (networkOperation) {
                is NetworkOperation.Failure -> "Failure"
                is NetworkOperation.Loading -> "Loading"
                is NetworkOperation.Success -> "Up to date"
            },
            modifier = Modifier
                .padding(top = 48.dp)
                .background(
                    color = backgroundColor.value,
                    shape = MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
                .clickable { onRefresh() }
                .padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }
}

@Composable
fun DailyQuoteContent(
    quoteText: String,
    quoteAuthor: String,
    isFavourite: Boolean,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(horizontal = 32.dp)
                .background(color = Color.Transparent, shape = MaterialTheme.shapes.large)
                .clip(shape = MaterialTheme.shapes.large)
        ) {
            Text(
                text = quoteText,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 32.dp, vertical = 50.dp),
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )
            Text(
                text = quoteAuthor,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End
            )

            Box(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 16.dp)
                    .size(60.dp)
                    .align(Alignment.BottomStart)
                    .background(color = Color.Black, shape = RoundedCornerShape(50))
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        onClick?.invoke()
                    }
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }

    }
}