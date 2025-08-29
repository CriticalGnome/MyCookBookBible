package com.criticalgnome.cookbook.ui.element

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme

@Composable
fun BottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.title)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            )
        }
    }
}

private data class BottomNavItem(
    @param:StringRes val title: Int,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(
        title = R.string.bottom_menu_home,
        route = "home",
        icon = Icons.Filled.Home
    ),
    BottomNavItem(
        title = R.string.bottom_menu_favorites,
        route = "favorites",
        icon = Icons.Filled.Favorite
    ),
    BottomNavItem(
        title = R.string.bottom_menu_photos,
        route = "photos",
        icon = Icons.Filled.Star
    ),
    BottomNavItem(
        title = R.string.bottom_menu_shopping,
        route = "shopping",
        icon = Icons.Filled.ShoppingCart
    )
)

@Preview(name = "Light Theme")
@Composable
private fun BottomBarPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        BottomBar(
            currentRoute = "home",
            onNavigate = {}
        )
    }
}

@Preview(
    name = "Dark Theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun BottomBarPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        BottomBar(
            currentRoute = "home",
            onNavigate = {}
        )
    }
}