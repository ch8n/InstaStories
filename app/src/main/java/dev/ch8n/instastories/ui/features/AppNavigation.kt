package dev.ch8n.instastories.ui.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.ch8n.instastories.ui.features.storiesHome.StoriesHomeScreen
import dev.ch8n.instastories.ui.features.storiesPreview.StoriesPreviewScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.StoriesHome.route
    ) {
        composable(Screen.StoriesHome.route) {
            StoriesHomeScreen(navController = navController)
        }

        composable(Screen.StoriesPreview("").route) {
            StoriesPreviewScreen(navController = navController)
        }
    }
}


sealed class Screen(val route: String) {
    data object StoriesHome : Screen(route = "stories_home")
    data class StoriesPreview(val storyId: String) : Screen(route = "stories_preview")
}