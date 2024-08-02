package dev.ch8n.instastories.ui.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.ch8n.instastories.ui.features.storiesHome.StoriesHomeScreen
import dev.ch8n.instastories.ui.features.storiesPreview.StoriesPreviewScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.StoriesHomeScreen
    ) {

        composable<Screen.StoriesHomeScreen> {
            StoriesHomeScreen(navController = navController)
        }

        composable<Screen.StoriesPreviewScreen> { backStackEntry ->
            StoriesPreviewScreen(
                navController = navController,
                backStackEntry = backStackEntry,
            )
        }
    }
}


@Serializable
sealed class Screen {

    @Serializable
    data object StoriesHomeScreen : Screen()

    @Serializable
    data class StoriesPreviewScreen(
        val storyId: String
    ) : Screen()
}