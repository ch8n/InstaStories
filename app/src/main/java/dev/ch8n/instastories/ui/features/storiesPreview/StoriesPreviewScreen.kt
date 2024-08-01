package dev.ch8n.instastories.ui.features.storiesPreview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ch8n.instastories.data.remote.config.RemoteServiceProvider
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.ui.features.storiesHome.components.CircularUserIcon
import dev.ch8n.instastories.utils.randomColor
import kotlinx.coroutines.launch


@Preview
@Composable
private fun StoriesPreviewScreenPreview() {
    val repository = remember {
        StoryRepository(RemoteServiceProvider.storiesService)
    }
    val fetchUseCase = remember {
        GetStoriesRemoteUseCase(repository)
    }
    val viewModel = remember { StoryPreviewViewModelViewModel(fetchUseCase) }
    val screenState by viewModel.screenState.collectAsState()
    StoriesPreviewScreen(screenState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoriesPreviewScreen(
    storiesHomeState: StoriesPreviewHomeState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { storiesHomeState.stories.size }
        )

        val scope = rememberCoroutineScope()

        val onLeftClicked by rememberUpdatedState {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }

        val onRightClicked by rememberUpdatedState {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false,
            beyondBoundsPageCount = 2,
        ) { pageNumber ->

            val configuration = LocalConfiguration.current
            val widthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(randomColor())
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset: Offset ->
                                when {
                                    offset.x < widthPx / 3 -> onLeftClicked.invoke()
                                    offset.x > 2 * widthPx / 3 -> onRightClicked.invoke()
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pageNumber.toString(),
                    color = Color.Black,
                    fontSize = 34.sp
                )
            }
        }
    }
}