package com.example.onequote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onequote.ui.theme.OneQuoteTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneQuoteTheme {
                OneQuote()
            }
        }
    }
}

data class Page(val title: String, val color: Color)

@Composable
fun HeaderNavigation(
    navigation: AppState.Navigation,
    onClick: (Page) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large.copy(
                    topStart = CornerSize(0), topEnd = CornerSize(0)
                )
            )
            .padding(vertical = 16.dp)
    ) {
        navigation.navItems.forEach {
            val isSelected = it.title == navigation.selectedPage.title
            val weight = if (isSelected) FontWeight.Normal else FontWeight.Light
            Text(text = it.title,
                fontWeight = weight,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { onClick(it) }
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .thenIf(isSelected) {
                        Modifier.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = MaterialTheme.shapes.medium
                        )

                    }
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

    }
}

@Composable
fun OneQuote(
    mainViewModel: MainActivityViewModel = viewModel()
) {
    val appState = mainViewModel.appState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        HeaderNavigation(
            navigation = appState.value.navigation,
            onClick = { mainViewModel.selectPage(it) })
        //Page content

        val selectedPage = appState.value.navigation.selectedPage
        when (selectedPage.title) {
            "Page 1" -> {
                TempContent(selectedPage.color)
            }

            "Page 2" -> {
                TempContent(selectedPage.color)
                TempContent(selectedPage.color)
            }

            "Page 3" -> {
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

inline fun Modifier.thenIf(predicate: Boolean, modify: () -> Modifier): Modifier {
    return this.then(if (predicate) modify() else Modifier)
}
