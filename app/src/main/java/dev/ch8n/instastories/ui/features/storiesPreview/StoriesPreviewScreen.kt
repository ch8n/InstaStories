package dev.ch8n.instastories.ui.features.storiesPreview

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.ch8n.instastories.data.remote.config.RemoteServiceProvider
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.ui.features.storiesPreview.component.AutoScrollingPager
import dev.ch8n.instastories.utils.randomColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
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
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (storiesHomeState.stories.isNotEmpty()) {


            AutoScrollingPager(
                modifier = Modifier.fillMaxSize(),
                pageCount = { storiesHomeState.stories.size }
            ) { pageNumber ->

                val selectedStory = storiesHomeState.stories.get(pageNumber)

                AsyncImage(
                    model = selectedStory.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}