package com.criticalgnome.cookbook.feature.recipe.edit

import com.criticalgnome.cookbook.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditRecipeViewModel @Inject constructor(

) : BaseViewModel<EditRecipeViewModel.State, EditRecipeViewModel.Event>(State()) {

    data class State(val recipeId: Long? = null)
    sealed interface Event
}
