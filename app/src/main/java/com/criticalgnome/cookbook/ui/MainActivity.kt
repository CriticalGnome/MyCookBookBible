package com.criticalgnome.cookbook.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.criticalgnome.cookbook.feature.main.MainViewModel
import com.criticalgnome.cookbook.feature.recipe.edit.EditRecipeViewModel
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val editRecipeViewModel by viewModels<EditRecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCookBookBibleTheme {
                NavigationStack(
                    mainViewModel = mainViewModel,
                    editRecipeViewModel = editRecipeViewModel,
                )
            }
        }
    }
}
