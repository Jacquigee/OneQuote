package com.example.onequote.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onequote.AppState

/**
 * PROJECT NAME: One Quote
 * USER        : jacqui
 * EMAIL       : gitaujaquiline@gmail.com
 * DATE        : Aug, 8/2/23
 * TIME        : 11:00 PM
 */

@Composable
fun SingleQuoteListItem(
    quote: AppState.Quote,
    onFavoriteClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = Color.Magenta,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
    ) {
        Text(
            text = quote.displayText, modifier = Modifier.padding(16.dp),
            color = Color.White, fontSize = 22.sp
        )
        Row(
            modifier = Modifier
                .background(color = Color.Blue)
                .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (quote.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite Icon",
                tint = Color.Black,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .clickable { onFavoriteClicked() }
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = quote.author,
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
private fun SingleQuoteListViewItem(){

    val quote = AppState.Quote(
        displayText = "Always focus on the front windshield and not the rear view mirror.",
        author = "Colin Powell",
        isFavorite = false
    )
    Column() {
        SingleQuoteListItem(quote = quote, onFavoriteClicked = { })
        Spacer(modifier = Modifier.height(8.dp))
        SingleQuoteListItem(quote = quote.copy(isFavorite = true), onFavoriteClicked = {})
    }
}