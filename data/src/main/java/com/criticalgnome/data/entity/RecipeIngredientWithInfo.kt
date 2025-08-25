package com.criticalgnome.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeIngredientWithInfo(
    @Embedded val crossRef: RecipeIngredientCrossRef,
    @Relation(
        parentColumn = "ingredientId",
        entityColumn = "id"
    )
    val ingredient: IngredientEntity
)
