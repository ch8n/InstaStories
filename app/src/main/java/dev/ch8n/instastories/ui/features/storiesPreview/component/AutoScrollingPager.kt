package dev.ch8n.instastories.ui.features.storiesPreview.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ch8n.instastories.utils.randomColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoScrollingPager(
    modifier: Modifier = Modifier,
    pagerState : PagerState,
    autoScrollDelay: Long = 5000,
    pageContent: @Composable (pageNumber: Int) -> Unit
) {
    Box(
        modifier = modifier
    ) {

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

        var isPressing by remember { mutableStateOf(false) }

        var scrollJob: Job? = remember { null }

        DisposableEffect(key1 = Unit, isPressing) {

            if (isPressing) {
                scrollJob?.cancel()
                return@DisposableEffect onDispose { }
            }

            scrollJob = scope.launch(Dispatchers.IO) {
                while (true) {
                    delay(autoScrollDelay)
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }

            onDispose {
                scrollJob?.cancel()
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false,
            beyondViewportPageCount = 2,
        ) { pageNumber ->
            val configuration = LocalConfiguration.current
            val widthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset: Offset ->
                                when {
                                    offset.x < widthPx / 3 -> onLeftClicked.invoke()
                                    offset.x > 2 * widthPx / 3 -> onRightClicked.invoke()
                                }
                            },
                            onPress = {
                                isPressing = true
                                awaitRelease()
                                isPressing = false
                            }
                        )
                    },
            ) {
                pageContent.invoke(pageNumber)
            }
        }
    }
}