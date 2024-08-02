package dev.ch8n.instastories.ui.features.storiesHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ch8n.instastories.domain.injector.UseCasesProvider
import dev.ch8n.instastories.domain.models.Story
import dev.ch8n.instastories.domain.usecases.GetStoriesRemoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StoriesHomeState(
    val stories: List<Story>,
    val isLoading: Boolean,
    val error: String
) {
    companion object {
        val Empty = StoriesHomeState(
            stories = emptyList(),
            isLoading = false,
            error = ""
        )
    }
}

class StoryViewModel(
    private val getStoriesRemoteUseCase: GetStoriesRemoteUseCase = UseCasesProvider.getStoriesRemoteUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(StoriesHomeState.Empty)
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
                    it.copy(stories = stories)
                }
            }
        }
    }
}