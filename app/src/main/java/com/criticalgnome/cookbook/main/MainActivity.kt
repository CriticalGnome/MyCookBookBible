package com.criticalgnome.cookbook.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.criticalgnome.cookbook.ui.screen.MainScreen
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCookBookBibleTheme {
                MainScreen()
            }
        }
    }
}

@Preview(
    name = "Light Theme",
    showSystemUi = true
)
@Composable
private fun MainScreenPreviewSystemLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        MainScreen()
    }
}

@Preview(
    name = "Dark Theme",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MainScreenPreviewSystemDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        MainScreen()
    }
}
