package com.criticalgnome.cookbook.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import com.criticalgnome.domain.entity.Recipe

@Composable
fun RecipeCard(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    onClick: (Long) -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(all = 4.dp)
            .clickable(onClick = { onClick(recipe.id) }),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Box {
            Image(
                modifier = Modifier.wrapContentHeight(),
                painter = painterResource(R.mipmap.sample),
                contentDescription = "Recipe Photo",
                contentScale = ContentScale.Crop,
            )
            if (recipe.isFavorite) {
                Icon(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .align(Alignment.TopEnd),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                )
            }
        }
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                text = recipe.title,
            )
            recipe.rating.takeIf { it > 0 }?.let {
                Text(
                    modifier = modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    text = "Rating: ${it / 2}.${(it % 2) * 5} stars"
                )
            }
            recipe.description?.let {
                Text(
                    modifier = modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text = it,
                )
            }
        }
    }
}

@Composable
@Preview(name = "Light Theme")
private fun RecipeCardPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        RecipeCard(
            modifier = Modifier.width(250.dp),
            recipe = recipeStub,
        )
    }
}

@Composable
@Preview(name = "Dark Theme")
private fun RecipeCardPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        RecipeCard(
            modifier = Modifier.width(250.dp),
            recipe = recipeStub,
        )
    }
}

private val recipeStub = Recipe(
    id = 0L,
    title = "Recipe #1",
    description = "This is a temporary recipe description for list demonstration",
    comment = null,
    rating = 9,
    isFavorite = true,
    ingredients = emptyList(),
    steps = emptyList(),
    photos = emptyList(),
)
