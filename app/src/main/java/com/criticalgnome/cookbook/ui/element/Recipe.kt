package com.criticalgnome.cookbook.ui.element

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
) {
    Card(
        modifier = modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
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
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(120.dp)
            ) {
                Image(
                    modifier = Modifier.wrapContentHeight(),
                    painter = painterResource(R.mipmap.sample),
                    contentDescription = "Recipe Photo",
                    contentScale = ContentScale.Crop
                )
            }
//            Box {
//                Image(
//                    painter = painterResource(R.mipmap.sample),
//                    contentDescription = "Recipe Photo",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.width(120.dp)
//                )
//            }
//                if (recipe.isFavorite) {
//                    Icon(
//                        modifier = Modifier.padding(start = 8.dp),
//                        tint = MaterialTheme.colorScheme.primary,
//                        imageVector = Icons.Filled.Favorite,
//                        contentDescription = "Favorite",
//                    )
//                }
        }
    }
}

private val recipe = Recipe(
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

@Composable
@Preview(name = "Light Theme")
private fun RecipeCardPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        RecipeCard(recipe = recipe)
    }
}

@Composable
@Preview(name = "Dark Theme")
private fun RecipeCardPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        RecipeCard(recipe = recipe)
    }
}
