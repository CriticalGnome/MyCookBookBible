package com.criticalgnome.cookbook.feature.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.di.DispatchersManagerImpl
import com.criticalgnome.cookbook.feature.common.AppTopBar
import com.criticalgnome.cookbook.feature.common.BottomBar
import com.criticalgnome.cookbook.ui.NavigationTarget
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.main_screen_header),
                onMenuClick = {}, // TODO Open menu
                onAvatarClick = {}, // TODO Open profile screen
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = NavigationTarget.Home,
                onNavigate = { route -> navController.navigate(route) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavigationTarget.EditRecipe()) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.fab_add_recipe)
                )

            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            RecipeCardList(
                recipes = state.recipes,
                onItemClick = { navController.navigate(NavigationTarget.EditRecipe(recipeId = it)) }
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
@Preview(name = "Light Theme", showSystemUi = true)
@SuppressLint("ViewModelConstructorInComposable")
private fun MainScreenPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        MainScreen(
            viewModel = PreviewMainViewModel(),
            navController = rememberNavController(),
        )
    }
}

@Composable
@Preview(name = "Dark Theme", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@SuppressLint("ViewModelConstructorInComposable")
private fun MainScreenPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        MainScreen(
            viewModel = PreviewMainViewModel(),
            navController = rememberNavController(),
        )
    }
}

private class PreviewMainViewModel : MainViewModel(
    recipeRepository = StubRecipeRepository(),
    dispatchersManager = DispatchersManagerImpl(),
) {
    override val state = MutableStateFlow(State(recipes = recipesStub)).asStateFlow()
}

private class StubRecipeRepository : RecipeRepository {
    override fun allRecipesFlow() = flowOf(recipesStub)
    override suspend fun getRecipe(id: Long): Recipe? = null
    override suspend fun saveRecipe(recipe: Recipe): Long = 0L
    override suspend fun deleteRecipe(id: Long) = Unit
}

private val recipesStub: List<Recipe>
    get() = (1..20).mapIndexed { index, _ ->
        Recipe(
            id = index.toLong(),
            title = "Recipe #$index",
            description = descriptionStub,
            comment = null,
            rating = (4..10).random(),
            isFavorite = true,
            ingredients = emptyList(),
            steps = emptyList(),
            photos = emptyList(),
        )
    }

private val descriptionStub: String
    get() {
        val loremIpsum =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum tempor enim sit amet nisl blandit sagittis. Praesent non sapien ac dolor mollis dignissim at nec diam. Nunc enim mi, porttitor eget arcu ut, gravida tincidunt mauris. Maecenas nibh neque, tempus eu congue vel, imperdiet vel erat. Aenean rutrum efficitur ex. Phasellus commodo dictum facilisis. Proin pellentesque aliquet fermentum. Etiam congue tincidunt metus eu commodo. Pellentesque mollis lacus aliquet tincidunt imperdiet. Praesent pharetra lorem sed urna pellentesque, sit amet fringilla metus sodales. Sed ultrices faucibus mattis. Vivamus in risus eu velit pulvinar semper. Donec libero ligula, feugiat at est in, tempus."
        val length = (5..20).random()
        val words = loremIpsum.split(" ")
        return words.take(length).joinToString(" ") + if (length < words.size) "..." else ""
    }
