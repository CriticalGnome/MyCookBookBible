package com.criticalgnome.data.repository

import com.criticalgnome.data.dao.RecipeDao
import com.criticalgnome.data.entity.RecipeEntity
import com.criticalgnome.data.entity.RecipeWithDetails
import com.criticalgnome.data.mapper.RecipeMapper
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RecipeRepositoryImplTest {

    private val mockRecipeDao = mockk<RecipeDao>(relaxed = true)
    private val mockRecipeMapper = mockk<RecipeMapper>()
    private val recipe = mockk<Recipe>()
    private val recipeEntity = mockk<RecipeEntity>()
    private val recipeWithDetails = mockk<RecipeWithDetails>()
    private lateinit var sut: RecipeRepository

    @BeforeEach
    fun setUp() {
        sut = RecipeRepositoryImpl(mockRecipeDao, mockRecipeMapper)
        every { mockRecipeMapper.toDomain(recipeWithDetails) } returns recipe
        every { mockRecipeMapper.toEntity(recipe) } returns recipeEntity
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(mockRecipeDao, mockRecipeMapper)
        clearAllMocks()
    }

    @Test
    fun `should return recipe when exists and null when not exists`() = runTest {
        // Arrange
        coEvery { mockRecipeDao.getRecipeWithDetails(any()) } returns recipeWithDetails andThen null

        // Act
        val result1 = sut.getRecipe(1L)
        val result2 = sut.getRecipe(2L)

        // Assert
        assertNotNull(result1)
        assertEquals(recipe, result1)
        assertNull(result2)
        coVerify(exactly = 2) { mockRecipeDao.getRecipeWithDetails(any()) }
        verify(exactly = 1) { mockRecipeMapper.toDomain(recipeWithDetails) }
    }

    @Test
    @Suppress("UnusedFlow")
    fun `should return mapped recipes flow`() = runTest {
        // Arrange
        coEvery { mockRecipeDao.allRecipesFlow() } returns flowOf(listOf(recipeWithDetails))

        // Act
        val result = sut.allRecipesFlow()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.count())
        assertEquals(recipe, result.first().first())
        coVerify(exactly = 1) { mockRecipeDao.allRecipesFlow() }
        verify(exactly = 2) { mockRecipeMapper.toDomain(recipeWithDetails) }
    }

    @Test
    fun `should insert or update recipe`() = runTest {
        // Arrange
        coEvery { mockRecipeDao.insertRecipe(any()) } returns 1L
        coEvery { mockRecipeDao.updateRecipe(any()) } returns Unit
        every { recipeEntity.id } returns 1L
        every { recipe.id } returns 0L andThen 1L

        // Act
        val result1 = sut.saveRecipe(recipe)
        val result2 = sut.saveRecipe(recipe)

        // Assert
        assertEquals(1L, result1)
        assertEquals(1L, result2)
        coVerify(exactly = 1) { mockRecipeDao.insertRecipe(any()) }
        coVerify(exactly = 1) { mockRecipeDao.updateRecipe(any()) }
        verify(exactly = 2) { mockRecipeMapper.toEntity(recipe) }
    }

    @Test
    fun `should delete recipe`() = runTest {
        // Arrange
        coEvery { mockRecipeDao.getRecipeWithDetails(any()) } returns recipeWithDetails
        every { recipeWithDetails.recipe } returns recipeEntity

        // Act
        sut.deleteRecipe(1L)

        // Assert
        coVerify(exactly = 1) { mockRecipeDao.getRecipeWithDetails(1L) }
        coVerify(exactly = 1) { mockRecipeDao.deleteRecipe(recipeEntity) }
    }
}
