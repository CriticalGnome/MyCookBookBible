package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.RecipeEntity
import com.criticalgnome.data.entity.RecipeWithDetails
import com.criticalgnome.domain.entity.Ingredient
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.entity.Step
import javax.inject.Inject

interface RecipeMapper {
    fun toDomain(recipeWithDetails: RecipeWithDetails): Recipe
    fun toEntity(recipe: Recipe): RecipeEntity
}

class RecipeMapperImpl @Inject constructor() : RecipeMapper {
    override fun toDomain(recipeWithDetails: RecipeWithDetails): Recipe {
        return Recipe(
            id = recipeWithDetails.recipe.id,
            title = recipeWithDetails.recipe.title,
            description = recipeWithDetails.recipe.description,
            comment = recipeWithDetails.recipe.comment,
            rating = recipeWithDetails.recipe.rating,
            isFavorite = recipeWithDetails.recipe.isFavorite,
            ingredients = recipeWithDetails.ingredients.map { Ingredient(it.ingredient.id, it.ingredient.name, it.crossRef.quantity) },
            steps = recipeWithDetails.steps.map { Step(it.stepNumber, it.instruction) },
            photos = recipeWithDetails.photos.map { it.uri },
        )
    }

    override fun toEntity(recipe: Recipe): RecipeEntity {
        return RecipeEntity(
            id = recipe.id,
            title = recipe.title,
            description = recipe.description,
            comment = recipe.comment,
            rating = recipe.rating,
            isFavorite = recipe.isFavorite,
        )
    }
}
