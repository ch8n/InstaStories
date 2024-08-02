package dev.ch8n.instastories.domain.usecases

import com.google.common.truth.Truth
import dev.ch8n.instastories.data.repositories.StoryRepository
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.utils.ResultOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetStoriesRemoteUseCaseTest {

    private lateinit var storyRepository: StoryRepository
    private lateinit var getStoriesRemoteUseCase: GetStoriesRemoteUseCase

    @Before
    fun setUp() {
        storyRepository = mockk()
        getStoriesRemoteUseCase = GetStoriesRemoteUseCase(storyRepository)
    }

    @Test
    fun `invoke returns list of stories when repository fetch is successful`() = runBlocking {
        val fakeStories = listOf(
            Story("1", "https://example.com/image1.jpg", "user1"),
            Story("2", "https://example.com/image2.jpg", "user2")
        )
        coEvery { storyRepository.fetchStories() } returns ResultOf.Success(fakeStories)

        val result = getStoriesRemoteUseCase()
        val success = (result as ResultOf.Success)
        Truth.assertThat(success.value).isNotEmpty()
        Truth.assertThat(success.value).isEqualTo(fakeStories)
    }

    @Test
    fun `invoke returns failure when repository fetch fails`() = runBlocking {
        val exception = Exception("Network error")
        coEvery { storyRepository.fetchStories() } returns ResultOf.Error(
            exception.message,
            exception.cause
        )

        val result = getStoriesRemoteUseCase()
        val failure = (result as ResultOf.Error)
        Truth.assertThat(failure.message).isEqualTo("Network error")
    }
}