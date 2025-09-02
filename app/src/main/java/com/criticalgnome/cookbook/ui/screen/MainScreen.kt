package com.criticalgnome.cookbook.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.main.MainViewModel
import com.criticalgnome.cookbook.ui.element.AppTopBar
import com.criticalgnome.cookbook.ui.element.BottomBar
import com.criticalgnome.cookbook.ui.element.RecipeCardList
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import com.criticalgnome.domain.entity.Recipe

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    currentRoute: String = "home",
    state: MainViewModel.State = MainViewModel.State(),
    onNavigate: (String) -> Unit = {},
    onMenuClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
) {
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
        }
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
private fun MainScreenPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        MainScreen(state = stateStub)
    }
}

@Preview(
    name = "Dark Theme",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MainScreenPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        MainScreen(state = stateStub)
    }
}

private val stateStub = MainViewModel.State(
    recipes = (1..20).mapIndexed { index, _ ->
        Recipe(
            id = index.toLong(),
            title = "Recipe #1",
            description = "This is a temporary recipe description for list demonstration",
            comment = null,
            rating = 9,
            isFavorite = true,
            ingredients = emptyList(),
            steps = emptyList(),
            photos = emptyList(),
        )
    }
)
