package com.example.onequote

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onequote.navigation.HeaderNavigation

@Composable
fun PerformOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    onStart: () -> Unit = {},
    onResume: () -> Unit = {}
) {
    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onStart()
                Lifecycle.Event.ON_START -> onResume()
                else -> Log.i("OBSERVER", "LIFECYCLE: ${event.name}")
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        return@DisposableEffect onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OneQuote(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    mainViewModel: MainActivityViewModel = viewModel()
) {
    val appState by mainViewModel.appState.collectAsStateWithLifecycle()

    PerformOnLifecycle(
        lifecycleOwner = lifecycleOwner,
        onStart = { mainViewModel.fetchQuoteOfTheDay() },
        onResume = { })

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

            "Daily quote" -> DailyQuoteContent(
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
