package com.criticalgnome.cookbook.main

import androidx.lifecycle.viewModelScope
import com.criticalgnome.cookbook.base.BaseViewModel
import com.criticalgnome.cookbook.di.DispatchersManager
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchersManager: DispatchersManager,
): BaseViewModel<MainViewModel.State, MainViewModel.Event>(State()) {

    data class State(
        val isLoading: Boolean = false,
        val recipes: List<Recipe> = emptyList(),
    )

    sealed interface Event

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        setState { State::isLoading set true }
        viewModelScope.launch(dispatchersManager.io) {
            recipeRepository.allRecipesFlow().collect { recipes ->
                setState {
                    State::recipes set recipes
                    State::isLoading set false
                }
            }
        }
    }
}
