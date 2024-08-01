package dev.ch8n.instastories.ui.features.storiesPreview.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoScrollPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    indicatorWidth: Dp = 45.dp,
    indicatorCount: Int,
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    var autoScrollJob: Job? = remember { null }
    val config = LocalConfiguration.current
    val dashWidthPx = with(LocalDensity.current) { indicatorWidth.toPx() }
    val screenWidthWidthPx = with(LocalDensity.current) { config.screenWidthDp.dp.toPx() }
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = scrollState
    ) {

        items(indicatorCount) { index ->
            val currentIndicator = pagerState.currentPage
            val isCurrentIndicator = currentIndicator == index
            val scrollOffset =
                (index + 1) * dashWidthPx.roundToInt() - screenWidthWidthPx.roundToInt()
            DisposableEffect(key1 = Unit, isCurrentIndicator) {
                if (isCurrentIndicator) {
                    autoScrollJob?.cancel()
                    autoScrollJob = scope.launch {
                        scrollState.animateScrollToItem(index, scrollOffset)
                    }
                }
                onDispose {
                    autoScrollJob?.cancel()
                }
            }

            val indicatorColorModifier = if (currentIndicator >= index) {
                Modifier.background(Color.Gray, RoundedCornerShape(8.dp))
            } else {
                Modifier.background(
                    Color.LightGray.copy(alpha = 0.5f),
                    RoundedCornerShape(8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .width(44.dp)
                    .height(3.dp)
                    .then(indicatorColorModifier)
            )
        }
    }
}