package com.example.onequote

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {

    private val _appState = MutableStateFlow(AppState.initial())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    fun selectPage(page: Page) {
        _appState.update {
            return@update it.copy(
                navigation = it.navigation.copy(
                    selectedPage = page
                )
            )
        }
    }
}

data class AppState(
    val navigation: Navigation
) {
    data class Navigation(
        val navItems: List<Page>, val selectedPage: Page
    )

    companion object {
        fun initial(): AppState {
            val pages = buildList {
                add(Page("Page 1", Color.Red))
                add(Page("Page 2", Color.Green))
                add(Page("Page 3", Color.Blue))
            }
            return AppState(
                navigation = Navigation(
                    navItems = pages, selectedPage = pages[0]
                )
            )
        }
    }
}