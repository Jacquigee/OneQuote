package com.example.onequote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.example.onequote.ui.theme.OneQuoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

inline fun Modifier.thenIf(predicate: Boolean, modify: () -> Modifier): Modifier {
    return this.then(if (predicate) modify() else Modifier)
}