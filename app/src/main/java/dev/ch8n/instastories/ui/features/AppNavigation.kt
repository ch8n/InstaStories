package dev.ch8n.instastories.ui.features

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
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
        startDestination = Screen.StoriesHomeScreen,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
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