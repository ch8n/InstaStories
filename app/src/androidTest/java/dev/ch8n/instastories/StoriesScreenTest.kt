package dev.ch8n.instastories

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.ui.features.storiesHome.StoriesHomeContent
import dev.ch8n.instastories.ui.features.storiesHome.StoriesHomeState
import dev.ch8n.instastories.ui.theme.InstaStoriesTheme
import dev.ch8n.instastories.utils.TestTags
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import org.junit.Rule
import org.junit.Test

class StoriesScreenTest {

    /***
     * Test cases
     * 1. on loading 5 place holder circular icon is visible
     * 2. on error error message should be visible
     * 3. on data success
     *     1. empty data no circular icon
     *     2. 5 item data 5 circular icon
     *     3. Circular icon should have right initials
     */

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun storiesHomeScreen_onLoading_5_placeHolder_circular_icon_is_visible() {
        composeTestRule.setContent {
            InstaStoriesTheme {
                StoriesHomeContent(
                    storiesHomeState = StoriesHomeState(
                        stories = listOf(),
                        isLoading = true,
                        error = ""
                    ),
                    navigateToPreview = {}
                )
            }
        }

        composeTestRule
            .onAllNodesWithTag(TestTags.StoriesScreen.CIRCULAR_IMAGE_LOADER)
            .assertCountEquals(5)
    }

    @Test
    fun storiesHomeScreen_on_error_message_should_be_visible() {
        composeTestRule.setContent {
            InstaStoriesTheme {
                StoriesHomeContent(
                    storiesHomeState = StoriesHomeState(
                        stories = listOf(),
                        isLoading = false,
                        error = "error"
                    ),
                    navigateToPreview = {}
                )
            }
        }

        composeTestRule
            .onNode(hasTestTag(TestTags.StoriesScreen.ERROR_PLACEHOLDER))
            .assertIsDisplayed()

        composeTestRule
            .onNode(hasTestTag(TestTags.StoriesScreen.ERROR_PLACEHOLDER_TEXT))
            .assertTextEquals("error")

    }


    @Test
    fun storiesHomeScreen_on_data_empty_no_circular_icon() {
        composeTestRule.setContent {
            InstaStoriesTheme {
                StoriesHomeContent(
                    storiesHomeState = StoriesHomeState(
                        stories = listOf(),
                        isLoading = false,
                        error = ""
                    ),
                    navigateToPreview = {}
                )
            }
        }

        composeTestRule
            .onAllNodes(hasTestTag(TestTags.StoriesScreen.ERROR_PLACEHOLDER))
            .assertCountEquals(0)
    }


    @Test
    fun storiesHomeScreen_on_3_data_shows_3_circular_icon() {
        val fakeData = listOf(
            Story(
                id = "ipsum1",
                imageUrl = "http://www.bing.com/search?q=doctus",
                userName = "Horace Nichols"
            ),
            Story(
                id = "ipsum2",
                imageUrl = "http://www.bing.com/search?q=doctus",
                userName = "Rogelio Cline",

                ),
            Story(
                id = "ipsum3",
                imageUrl = "http://www.bing.com/search?q=doctus",
                userName = "Micah Reynolds",
            )
        )
        composeTestRule.setContent {
            InstaStoriesTheme {
                StoriesHomeContent(
                    storiesHomeState = StoriesHomeState(
                        stories = fakeData,
                        isLoading = false,
                        error = ""
                    ),
                    navigateToPreview = {}
                )
            }
        }

        composeTestRule
            .onAllNodes(hasTestTag(TestTags.StoriesScreen.CIRCULAR_IMAGE))
            .assertCountEquals(fakeData.size)
    }


    @Test
    fun storiesHomeScreen_on_data_circular_icon_shows_correct_initals() {
        composeTestRule.setContent {
            InstaStoriesTheme {
                StoriesHomeContent(
                    storiesHomeState = StoriesHomeState(
                        stories = listOf(
                            Story(
                                id = "ipsum1",
                                imageUrl = "http://www.bing.com/search?q=doctus",
                                userName = "Horace Nichols"
                            ),
                            Story(
                                id = "ipsum1",
                                imageUrl = "http://www.bing.com/search?q=doctus",
                                userName = "Harry Potter"
                            ),
                        ),
                        isLoading = false,
                        error = ""
                    ),
                    navigateToPreview = {}
                )
            }
        }

        composeTestRule
            .onAllNodes(hasTestTag(TestTags.StoriesScreen.CIRCULAR_IMAGE))
            .assertCountEquals(2)
            .onFirst()
            .assert(hasText("HN"))
    }
}