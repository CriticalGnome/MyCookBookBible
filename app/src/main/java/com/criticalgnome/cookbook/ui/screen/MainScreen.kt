package com.criticalgnome.cookbook.ui.screen

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.di.DispatchersManagerImpl
import com.criticalgnome.cookbook.main.MainViewModel
import com.criticalgnome.cookbook.ui.element.AppTopBar
import com.criticalgnome.cookbook.ui.element.BottomBar
import com.criticalgnome.cookbook.ui.element.RecipeCardList
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    currentRoute: String = "home",
    viewModel: MainViewModel = viewModel(),
    onNavigate: (String) -> Unit = {},
    onMenuClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.main_screen_header),
                onMenuClick = onMenuClick,
                onAvatarClick = onAvatarClick
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate("add") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Recipe"
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
            RecipeCardList(recipes = state.recipes)
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(
    name = "Light Theme",
    showSystemUi = true
)
@Composable
@SuppressLint("ViewModelConstructorInComposable")
private fun MainScreenPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        MainScreen(viewModel = PreviewMainViewModel())
    }
}

@Preview(
    name = "Dark Theme",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
@SuppressLint("ViewModelConstructorInComposable")
private fun MainScreenPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        MainScreen(viewModel = PreviewMainViewModel())
    }
}

class PreviewMainViewModel : MainViewModel(
    recipeRepository = StubRecipeRepository(),
    dispatchersManager = DispatchersManagerImpl(),
) {
    override val state = MutableStateFlow(State(recipes = recipesStub)).asStateFlow()
}

private class StubRecipeRepository : RecipeRepository {
    override fun allRecipesFlow() = flowOf(recipesStub)
    override suspend fun getRecipe(id: Long): Recipe? = null
    override suspend fun addRecipe(recipe: Recipe): Long = 0L
    override suspend fun deleteRecipe(id: Long) = Unit
}

private val recipesStub: List<Recipe>
    get() = (1..20).mapIndexed { index, _ ->
        Recipe(
            id = index.toLong(),
            title = "Recipe #1",
            description = descriptionStub,
            comment = null,
            rating = 9,
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
        val length = (5..50).random()
        val words = loremIpsum.split(" ")
        return words.take(length).joinToString(" ") + if (length < words.size) "..." else ""
    }
