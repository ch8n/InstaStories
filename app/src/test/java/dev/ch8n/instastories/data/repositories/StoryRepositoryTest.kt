package dev.ch8n.instastories.data.repositories

import org.junit.Assert.*

import com.google.common.truth.Truth.assertThat
import dev.ch8n.instastories.data.remote.StoriesService
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.utils.ResultOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class StoryRepositoryTest {

    private lateinit var storiesService: StoriesService
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        storiesService = mockk()
        storyRepository = StoryRepository(storiesService)
    }

    @Test
    fun `fetchStories returns list of stories when service call is successful`(): Unit =
        runBlocking {

            val fakeStories = listOf(
                Story("1", "https://example.com/image1.jpg", "user1"),
                Story("2", "https://example.com/image2.jpg", "user2")
            )
            coEvery { storiesService.getStories() } returns fakeStories

            val result = storyRepository.fetchStories()
            val success = (result as ResultOf.Success)
            assertThat(success.value).isNotEmpty()
            assertThat(success.value).isEqualTo(fakeStories)
        }

    @Test
    fun `fetchStories returns failure when service call fails`(): Unit = runBlocking {
        coEvery { storiesService.getStories() } throws Exception("Network error")
        val result = storyRepository.fetchStories()

        val failed = (result as ResultOf.Error)
        assertThat(failed.message).isEqualTo("Network error")
    }
}