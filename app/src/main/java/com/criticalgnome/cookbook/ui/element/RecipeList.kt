package com.criticalgnome.cookbook.ui.element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.criticalgnome.domain.entity.Recipe

@Composable
fun RecipeList(
    recipes: List<Recipe>,
) {
    LazyColumn {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe)
        }
    }
}

@Composable
@Preview(name = "Light Theme")
private fun RecipeListPreviewLight() {
    RecipeList(
        recipes = (1..20).mapIndexed { index, _ ->
            Recipe(
                id = index.toLong(),
                title = "Recipe #1",
                description = "This is a temporary recipe description for list demonstration",
                comment = null,
                rating = 9,
                isFavorite = true,
                ingredients = emptyList(),
                steps = emptyList(),
                photos = emptyList(),
            )
        }
    )
}
