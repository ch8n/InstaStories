package dev.ch8n.instastories.ui.features.storiesPreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ch8n.instastories.domain.injector.UseCasesProvider
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import dev.ch8n.instastories.utils.ResultOf
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
    private val getStoriesRemoteUseCase: GetStoriesRemoteUseCase = UseCasesProvider.getStoriesRemoteUseCase
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
                val storiesResult = getStoriesRemoteUseCase.invoke()
                when (storiesResult) {
                    is ResultOf.Error -> {
                        _screenState.update {
                            it.copy(error = storiesResult.message ?: "Something went wrong!")
                        }
                    }

                    is ResultOf.Success -> {
                        _screenState.update {
                            it.copy(stories = storiesResult.value)
                        }
                    }
                }
            }
        }
    }
}