package com.criticalgnome.cookbook.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.ui.element.AppTopBar
import com.criticalgnome.cookbook.ui.element.BottomBar
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme
import com.criticalgnome.domain.entity.Recipe

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    currentRoute: String = "home",
    onNavigate: (String) -> Unit = {},
    onMenuClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.main_screen_header),
                onMenuClick = onMenuClick,
                onAvatarClick = onAvatarClick
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {

        }
    }
}

@Composable
private fun PlaceholderRecipeCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "$title\n$description",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(
    name = "Light Theme",
    showSystemUi = true
)
@Composable
private fun MainScreenPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        MainScreen()
    }
}

@Preview(
    name = "Dark Theme",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MainScreenPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        MainScreen()
    }
}
