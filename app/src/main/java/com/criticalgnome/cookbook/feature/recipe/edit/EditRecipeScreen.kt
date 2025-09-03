package com.criticalgnome.cookbook.feature.recipe.edit

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.criticalgnome.cookbook.R
import com.criticalgnome.cookbook.feature.common.AppTopBar
import com.criticalgnome.cookbook.feature.common.BottomBar
import com.criticalgnome.cookbook.ui.theme.MyCookBookBibleTheme

@Composable
fun EditRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: EditRecipeViewModel = EditRecipeViewModel(),
    navController: NavController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.main_screen_header),
                onMenuClick = {}, // TODO Open menu
                onAvatarClick = {}, // TODO Open profile screen
            )
        },
        bottomBar = {
            BottomBar(
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(all = 8.dp)
        ) {
            var title = ""
            val titleMaxChars = 100
            var description = ""
            val descriptionMaxChars = 1000
            Text(text = "Title")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                placeholder = { Text(text = "Enter title here...") },
                onValueChange = { if (it.length <= titleMaxChars) title = it },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = "${title.length}/$titleMaxChars",

                        )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { /* действие при нажатии Next */ }
                ),
                maxLines = 1,
            )
            Text(text = "Description")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                placeholder = { Text(text = "Enter description here...") },
                onValueChange = { if (it.length <= descriptionMaxChars) description = it },
                supportingText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        text = "${description.length}/$descriptionMaxChars",

                        )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { /* действие при нажатии Next */ }
                ),
                maxLines = 2,
            )
            Text(text = "Ingredients")
            Text(text = "Cooking steps")
            Text(text = "Photos")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,

                ) {
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {}
                ) {
                    Text("Save")
                }
                OutlinedButton(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {}
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
@Preview(
    name = "Light theme",
    showSystemUi = true,
)
private fun EditRecipeScreenPreviewLight() {
    MyCookBookBibleTheme(darkTheme = false) {
        EditRecipeScreen()
    }
}

@Composable
@Preview(name = "Dark theme", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EditRecipeScreenPreviewDark() {
    MyCookBookBibleTheme(darkTheme = true) {
        EditRecipeScreen()
    }
}
