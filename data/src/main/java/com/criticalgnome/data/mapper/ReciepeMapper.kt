package com.criticalgnome.data.mapper

import com.criticalgnome.data.entity.RecipeEntity
import com.criticalgnome.data.entity.RecipeWithDetails
import com.criticalgnome.domain.entity.Ingredient
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.entity.Step

val RecipeWithDetails.asDomain: Recipe
    get() = Recipe(
        id = recipe.id,
        title = recipe.title,
        description = recipe.description,
        comment = recipe.comment,
        rating = recipe.rating,
        isFavorite = recipe.isFavorite,
        ingredients = ingredients.map { Ingredient(it.ingredient.id, it.ingredient.name, it.crossRef.quantity) },
        steps = steps.map { Step(it.stepNumber, it.instruction) },
        photos = photos.map { it.uri }
    )

val Recipe.asEntity: RecipeEntity
    get() = RecipeEntity(
        id = id,
        title = title,
        description = description,
        comment = comment,
        rating = rating,
        isFavorite = isFavorite
    )
