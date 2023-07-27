package com.example.onequote

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


@Composable
fun DailyQuoteScreen(
    networkOperation: NetworkOperation<AppState.Quote>, onFavouriteClicked: (AppState.Quote) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        when (networkOperation) {
            is NetworkOperation.Failure -> {}
            is NetworkOperation.Loading -> {}
            is NetworkOperation.Success -> {}
        }
       networkOperation.onLoading {

       }
        networkOperation.onFailure {

        }
        networkOperation.onSuccess {

        }
    }
}

@Composable
fun DailyQuoteContent(
    quote: AppState.Quote, onFavoriteQuote: (AppState.Quote) -> Unit
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
                text = quote.displayText,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 32.dp, vertical = 50.dp),
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive
            )
            Text(
                text = quote.author,
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
                        onFavoriteQuote(quote)
                    }
            ) {
                Icon(
                    imageVector = if (quote.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }

    }
}