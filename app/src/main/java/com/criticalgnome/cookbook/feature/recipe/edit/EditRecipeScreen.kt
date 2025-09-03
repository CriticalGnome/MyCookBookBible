package com.criticalgnome.cookbook.feature.recipe.edit

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.di.DispatchersManagerImpl
import com.criticalgnome.cookbook.feature.common.AppTopBar
import com.criticalgnome.cookbook.feature.common.BottomBar
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun EditRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: EditRecipeViewModel,
    navController: NavController,
    recipeId: Long? = null,
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()
    val event by viewModel.event.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(event) {
        when (event) {
            is EditRecipeViewModel.Event.RecipeSaved -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Recipe successfully saved",
                        actionLabel = "OK",
                        duration = SnackbarDuration.Short,
                    )
                }
            }

            null -> { /* No op */
            }
        }
        viewModel.consumeEvent()
    }

    if (recipeId != null) {
        viewModel.loadRecipe(recipeId)
    }

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
                onNavigate = { route -> navController.navigate(route) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(all = 8.dp)
        ) {
            val titleMaxChars = 100
            val descriptionMaxChars = 1000
            Text(text = "Title")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                placeholder = { Text(text = "Enter title here...") },
                onValueChange = { if (it.length <= titleMaxChars) viewModel.title = it },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = "${state.title.length}/$titleMaxChars",
                        color = if (state.title.length < titleMaxChars) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                        )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { /* do on Next */ }
                ),
                maxLines = 1,
            )
            Text(text = "Description")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description,
                placeholder = { Text(text = "Enter description here...") },
                onValueChange = { if (it.length <= descriptionMaxChars) viewModel.description = it },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = "${state.description.length}/$descriptionMaxChars",
                        color = if (state.description.length < descriptionMaxChars) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                        )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { /* do on Next */ }
                ),
                maxLines = 4,
            )
            Text(text = "Ingredients")
            Text(text = "Cooking steps")
            Text(text = "Photos")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,

                ) {
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    enabled = state.isSaveButtonEnabled,
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.saveRecipe()
                    }
                ) {
                    Text("Save")
                }
                OutlinedButton(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        viewModel.clearFields()
                        navController.navigateUp()
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
@Preview(name = "Light theme", showSystemUi = true)
@SuppressLint("ViewModelConstructorInComposable")
private fun EditRecipeScreenPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        EditRecipeScreen(
            viewModel = PreviewEditRecipeViewModel(),
            navController = rememberNavController(),
        )
    }
}

@Composable
@Preview(name = "Dark theme", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@SuppressLint("ViewModelConstructorInComposable")
private fun EditRecipeScreenPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        EditRecipeScreen(
            viewModel = PreviewEditRecipeViewModel(),
            navController = rememberNavController(),
        )
    }
}

private class PreviewEditRecipeViewModel : EditRecipeViewModel(
    recipeRepository = StubRecipeRepository(),
    dispatchersManager = DispatchersManagerImpl(),
)

private class StubRecipeRepository : RecipeRepository {
    override fun allRecipesFlow() = flowOf(emptyList<Recipe>())
    override suspend fun getRecipe(id: Long): Recipe? = null
    override suspend fun saveRecipe(recipe: Recipe): Long = 0L
    override suspend fun deleteRecipe(id: Long) = Unit
}
