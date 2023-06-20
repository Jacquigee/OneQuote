package com.example.onequote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onequote.navigation.HeaderNavigation

@Preview(showBackground = true)
@Composable
fun OneQuote(
    mainViewModel: MainActivityViewModel = viewModel()
) {
    val appState by mainViewModel.appState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        HeaderNavigation(
            navigation = appState.navigation,
            onClick = { mainViewModel.selectPage(it) })
        //Page content

        val selectedPage = appState.navigation.selectedPage
        when (selectedPage.title) {
            "All quotes" -> {
                TempContent(selectedPage.color)
            }

            "Daily quote" -> DailyQuoteScreen(
                quote = appState.quoteOfTheDay,
                onFavoriteQuote = {
                    //todo handle onClick
                })

            "Favorites" -> {
                TempContent(selectedPage.color)
                TempContent(selectedPage.color)
                TempContent(selectedPage.color)
            }
        }
    }
}
@Composable
private fun TempContent(itemColor: Color) {
    Box(
        modifier = Modifier
            .padding(all = 32.dp)
            .height(16.dp)
            .fillMaxWidth()
            .background(
                color = itemColor,
                shape = MaterialTheme.shapes.medium
            )
    )
}
