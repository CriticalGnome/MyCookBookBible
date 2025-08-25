package com.criticalgnome.data.repository

import com.criticalgnome.data.dao.RecipeDao
import com.criticalgnome.data.mapper.asDomain
import com.criticalgnome.data.mapper.asEntity
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override suspend fun getRecipe(id: Long): Recipe? {
        val recipeWithDetails = recipeDao.getRecipeWithDetails(id) ?: return null
        return recipeWithDetails.asDomain
    }

    override fun allRecipesFlow(): Flow<List<Recipe>> {
        return recipeDao.allRecipesFlow().map { it.map { recipeWithDetails -> recipeWithDetails.asDomain } }
    }

    override suspend fun addRecipe(recipe: Recipe): Long {
        val recipeEntity = recipe.asEntity
        return recipeDao.insertRecipe(recipeEntity)
    }

    override suspend fun deleteRecipe(id: Long) {
        recipeDao.getRecipeWithDetails(id)?.recipe?.let {
            recipeDao.deleteRecipe(it)
        }
    }
}
