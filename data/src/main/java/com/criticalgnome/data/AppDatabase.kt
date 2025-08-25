package com.criticalgnome.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.criticalgnome.data.dao.RecipeDao
import com.criticalgnome.data.entity.IngredientEntity
import com.criticalgnome.data.entity.PhotoEntity
import com.criticalgnome.data.entity.RecipeEntity
import com.criticalgnome.data.entity.RecipeIngredientCrossRef
import com.criticalgnome.data.entity.StepEntity

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        RecipeIngredientCrossRef::class,
        StepEntity::class,
        PhotoEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
