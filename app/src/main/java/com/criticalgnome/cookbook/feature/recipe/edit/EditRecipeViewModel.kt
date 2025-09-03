package com.criticalgnome.cookbook.feature.recipe.edit

import androidx.lifecycle.viewModelScope
import com.criticalgnome.cookbook.base.BaseViewModel
import com.criticalgnome.cookbook.di.DispatchersManager
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class EditRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchersManager: DispatchersManager,
) : BaseViewModel<EditRecipeViewModel.State, EditRecipeViewModel.Event>(State()) {

    data class State(
        val recipeId: Long? = null,
        val title: String = "",
        val description: String = "",
        val isSaveButtonEnabled: Boolean = false,
    )

    var title: String
        get() = state.value.title
        set(value) = updateState {
            copy(
                title = value,
                isSaveButtonEnabled = value.isNotBlank(),
            )
        }

    var description: String
        get() = state.value.description
        set(value) = updateState { copy(description = value) }

    sealed interface Event {
        data object RecipeSaved : Event
    }

    fun loadRecipe(id: Long) {
        viewModelScope.launch(dispatchersManager.io) {
            recipeRepository.getRecipe(id)?.let { recipe ->
                updateState {
                    copy(
                        recipeId = id,
                        title = recipe.title,
                        description = recipe.description ?: "",
                        isSaveButtonEnabled = recipe.title.isNotBlank(),
                    )
                }
            }
        }
    }

    fun saveRecipe() {
        viewModelScope.launch(dispatchersManager.io) {
            val recipe = Recipe(
                id = state.value.recipeId ?: 0L,
                title = state.value.title,
                description = state.value.description,
                comment = null,
                rating = 0,
                isFavorite = false,
                ingredients = emptyList(),
                steps = emptyList(),
                photos = emptyList(),
            )
            val id = recipeRepository.saveRecipe(recipe)
            updateState { copy(recipeId = id) }
            emitEvent(Event.RecipeSaved)
        }
    }

    fun clearFields() {
        updateState {
            copy(
                recipeId = null,
                title = "",
                description = "",
            )
        }
    }
}
