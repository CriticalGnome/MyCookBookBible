package com.criticalgnome.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.criticalgnome.data.entity.IngredientEntity
import com.criticalgnome.data.entity.PhotoEntity
import com.criticalgnome.data.entity.RecipeEntity
import com.criticalgnome.data.entity.RecipeIngredientCrossRef
import com.criticalgnome.data.entity.RecipeWithDetails
import com.criticalgnome.data.entity.StepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    // ====== Recipes ======
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    // ====== Ingredients ======
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: IngredientEntity): Long

    @Insert
    suspend fun insertRecipeIngredientCrossRef(crossRef: RecipeIngredientCrossRef)

    // ====== Steps ======
    @Insert
    suspend fun insertSteps(steps: List<StepEntity>)

    // ====== Photos ======
    @Insert
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    // ====== Query with Relations ======
    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeWithDetails(id: Long): RecipeWithDetails?

    @Transaction
    @Query("SELECT * FROM recipes ORDER BY createdAt DESC")
    fun allRecipesFlow(): Flow<List<RecipeWithDetails>>
}
