package dev.ch8n.instastories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.runComposeUiTest
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.ui.features.storiesPreview.StoriesPreviewContent
import dev.ch8n.instastories.ui.features.storiesPreview.StoriesPreviewHomeState
import dev.ch8n.instastories.ui.features.storiesPreview.component.AutoScrollingPager
import dev.ch8n.instastories.ui.theme.InstaStoriesTheme
import dev.ch8n.instastories.utils.TestTags
import org.junit.Rule
import org.junit.Test

class StoriesPreviewScreenTest {

    /***
     * Test cases
     * 1. auto scroll to next page after 5 sec
     * 2. on pressing doesn't auto scroll after 5 sec
     * 3. on press right page changes to next
     * 4. on press left page changes to previous
     */

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun testAutoScrollingPager_changesPageAfter5Seconds() {
        composeTestRule.setContent {
            val pagerState = rememberPagerState(
                0,
                pageCount = { 5 }
            )
            AutoScrollingPager(
                pagerState = pagerState,
                autoScrollDelay = 5000L,
                pageContent = { pageNumber ->
                    Text(text = "Page $pageNumber")
                }
            )
        }
        composeTestRule.onNodeWithText("Page 0").assertIsDisplayed()
        composeTestRule.mainClock.advanceTimeBy(5000L)
        composeTestRule.onNodeWithText("Page 1").assertIsDisplayed()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun testAutoScrollingPager_doesNotAutoScrollWhilePressing() {
        composeTestRule.setContent {
            val pagerState = rememberPagerState(
                0,
                pageCount = { 5 }
            )
            AutoScrollingPager(
                pagerState = pagerState,
                autoScrollDelay = 5000L,
                pageContent = { pageNumber ->
                    Text(text = "Page $pageNumber")
                }
            )
        }

        composeTestRule.onNodeWithText("Page 0").assertIsDisplayed()

        composeTestRule.onNodeWithText("Page 0").performTouchInput {
            down(center)
            advanceEventTime(5000L)
            up()
        }

        composeTestRule.onNodeWithText("Page 0").assertIsDisplayed()
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun testAutoScrollingPager_onPressRight_PageNext() {
        composeTestRule.setContent {
            val pagerState = rememberPagerState(
                0,
                pageCount = { 5 }
            )
            AutoScrollingPager(
                modifier = Modifier.testTag(TestTags.StoriesPreviewScreen.STORY_PAGER),
                pagerState = pagerState,
                autoScrollDelay = 5000L,
                pageContent = { pageNumber ->
                    Text(text = "Page $pageNumber")
                }
            )
        }

        composeTestRule.onNodeWithTag(TestTags.StoriesPreviewScreen.STORY_PAGER).assertIsDisplayed()
        composeTestRule.onNodeWithText("Page 0").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.StoriesPreviewScreen.STORY_PAGER)
            .performTouchInput {
                down(centerRight)
                up()
            }
        composeTestRule.onNodeWithText("Page 1").assertIsDisplayed()
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun testAutoScrollingPager_onPressLeft_PagePrevious() {
        composeTestRule.setContent {
            val pagerState = rememberPagerState(
                2,
                pageCount = { 5 }
            )
            AutoScrollingPager(
                modifier = Modifier.testTag(TestTags.StoriesPreviewScreen.STORY_PAGER),
                pagerState = pagerState,
                autoScrollDelay = 5000L,
                pageContent = { pageNumber ->
                    Text(text = "Page $pageNumber")
                }
            )
        }

        composeTestRule.onNodeWithTag(TestTags.StoriesPreviewScreen.STORY_PAGER).assertIsDisplayed()
        composeTestRule.onNodeWithText("Page 2").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.StoriesPreviewScreen.STORY_PAGER)
            .performTouchInput {
                down(centerLeft)
                up()
            }
        composeTestRule.onNodeWithText("Page 1").assertIsDisplayed()
    }
}