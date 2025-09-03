package com.criticalgnome.cookbook.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.criticalgnome.cookbook.feature.main.MainScreen
import com.criticalgnome.cookbook.feature.main.MainViewModel
import com.criticalgnome.cookbook.feature.recipe.edit.EditRecipeScreen
import com.criticalgnome.cookbook.feature.recipe.edit.EditRecipeViewModel
import kotlinx.serialization.Serializable


sealed interface NavigationTarget {
    @Serializable
    data object Home : NavigationTarget

    @Serializable
    data class EditRecipe(val recipeId: Long? = null) : NavigationTarget

    @Serializable
    data object Favorites : NavigationTarget

    @Serializable
    data object Photos : NavigationTarget

    @Serializable
    data object Shopping : NavigationTarget
}

@Composable
fun NavigationStack(
    mainViewModel: MainViewModel,
    editRecipeViewModel: EditRecipeViewModel,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationTarget.Home) {
        composable<NavigationTarget.Home> {
            MainScreen(
                viewModel = mainViewModel,
                navController = navController,
            )
        }
        composable<NavigationTarget.Favorites> {}
        composable<NavigationTarget.Photos> {}
        composable<NavigationTarget.Shopping> {}
        composable<NavigationTarget.EditRecipe> { target ->
            EditRecipeScreen(
                viewModel = editRecipeViewModel,
                navController = navController,
                recipeId = target.toRoute<NavigationTarget.EditRecipe>().recipeId,
            )
        }
    }
}
