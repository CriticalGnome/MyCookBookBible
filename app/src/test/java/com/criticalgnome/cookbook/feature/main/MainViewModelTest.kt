package com.criticalgnome.cookbook.feature.main

import com.criticalgnome.cookbook.di.TestDispatchersManagerImpl
import com.criticalgnome.domain.entity.Recipe
import com.criticalgnome.domain.repository.RecipeRepository
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MainViewModelTest {

    private val recipeRepository = mockk<RecipeRepository>()
    private val dispatchersManager = TestDispatchersManagerImpl()
    private val recipe = mockk<Recipe>()
    private lateinit var sut: MainViewModel

    @BeforeEach
    fun setUp() {
        sut = MainViewModel(
            recipeRepository = recipeRepository,
            dispatchersManager = dispatchersManager,
        )
    }

    @AfterEach
    fun tearDown() {
        confirmVerified(recipeRepository)
    }

    @Test
    @Suppress("UnusedFlow")
    @DisplayName("loadRecipes updates state with collected recipes")
    fun `loadRecipes updates state with collected recipes`() = runTest {
        // Arrange
        val recipes = listOf(recipe)
        coEvery { recipeRepository.allRecipesFlow() } returns flowOf(recipes)

        // Act
        sut.loadRecipes()

        // Assert
        assertTrue(sut.state.value.recipes == recipes)
        assertFalse(sut.state.value.isLoading)
        verify { recipeRepository.allRecipesFlow() }
    }
}
