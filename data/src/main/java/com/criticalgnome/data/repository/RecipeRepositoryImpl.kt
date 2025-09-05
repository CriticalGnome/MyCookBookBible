package com.criticalgnome.data.repository

import com.criticalgnome.data.dao.RecipeDao
import com.criticalgnome.data.mapper.RecipeMapper
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeMapper: RecipeMapper,
) : RecipeRepository {

    override suspend fun getRecipe(id: Long): Recipe? {
        val recipeWithDetails = recipeDao.getRecipeWithDetails(id) ?: return null
        return recipeMapper.toDomain(recipeWithDetails)
    }

    override fun allRecipesFlow(): Flow<List<Recipe>> {
        return recipeDao.allRecipesFlow().map { it.map { recipeWithDetails -> recipeMapper.toDomain(recipeWithDetails) } }
    }

    override suspend fun saveRecipe(recipe: Recipe): Long {
        val recipeEntity = recipeMapper.toEntity(recipe)
        return if (recipe.id == 0L) {
            recipeDao.insertRecipe(recipeEntity)
        } else {
            recipeDao.updateRecipe(recipeEntity)
            recipeEntity.id
        }
    }

    override suspend fun deleteRecipe(id: Long) {
        recipeDao.getRecipeWithDetails(id)?.recipe?.let {
            recipeDao.deleteRecipe(it)
        }
    }
}
