package dev.ch8n.instastories.ui.features.storiesPreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StoriesPreviewHomeState(
    val stories: List<Story>,
    val isLoading: Boolean,
    val error: String
) {
    companion object {
        val Empty = StoriesPreviewHomeState(
            stories = emptyList(),
            isLoading = true,
            error = ""
        )
    }
}

class StoryPreviewViewModelViewModel(
    private val getStoriesRemoteUseCase: GetStoriesRemoteUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(StoriesPreviewHomeState.Empty)
    val screenState = _screenState.asStateFlow()

    private suspend fun withLoading(action: suspend () -> Unit) {
        _screenState.update { it.copy(isLoading = true) }
        try {
            action.invoke()
        } finally {
            _screenState.update { it.copy(isLoading = false) }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withLoading {
                val stories = getStoriesRemoteUseCase.invoke()
                _screenState.update {
                    it.copy(
                        stories = stories
                    )
                }
            }
        }
    }
}