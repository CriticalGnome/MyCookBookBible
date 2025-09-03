package com.criticalgnome.cookbook.ui.element

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import com.criticalgnome.domain.entity.Recipe

@Composable
fun RecipeCardList(
    modifier: Modifier = Modifier,
    recipes: List<Recipe>,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (recipes.isNotEmpty()) {
            LazyVerticalStaggeredGrid (
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(recipes.size) { it ->
                    RecipeCard(recipe = recipes[it])
                }
            }
        } else {
            Text(
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 8.dp))
                    .align(Alignment.Center),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                text = "Your recipe book is empty. It's time to add your first record!"
            )
        }
    }
}

@Composable
@Preview(name = "Light Theme")
private fun RecipeListPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        RecipeCardList(recipes = recipesStub)
    }
}

@Composable
@Preview(name = "Dark Theme")
private fun RecipeListPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        RecipeCardList(recipes = recipesStub)
    }
}

private val recipesStub = (1..6).mapIndexed { index, _ ->
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
