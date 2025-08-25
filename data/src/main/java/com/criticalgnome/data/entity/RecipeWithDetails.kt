package com.criticalgnome.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithDetails(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId",
        entity = RecipeIngredientCrossRef::class
    )
    val ingredients: List<RecipeIngredientWithInfo>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val steps: List<StepEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val photos: List<PhotoEntity>
)
