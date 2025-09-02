package com.criticalgnome.cookbook.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.criticalgnome.cookbook.ui.screen.MainScreen
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state = viewModel.state.collectAsState()
            MyCookBookBibleTheme {
                MainScreen(state = state.value)
            }
        }
    }
}
