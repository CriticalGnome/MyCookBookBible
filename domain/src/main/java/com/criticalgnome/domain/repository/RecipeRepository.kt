package com.criticalgnome.domain.repository

import com.criticalgnome.domain.entity.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipe(id: Long): Recipe?
    fun allRecipesFlow(): Flow<List<Recipe>>
    suspend fun addRecipe(recipe: Recipe): Long
    suspend fun deleteRecipe(id: Long)
}
