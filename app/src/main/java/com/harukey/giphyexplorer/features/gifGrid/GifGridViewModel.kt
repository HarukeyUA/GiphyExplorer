package com.harukey.giphyexplorer.features.gifGrid

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.harukey.giphyexplorer.core.domain.interactor.GifInteractor
import com.harukey.giphyexplorer.features.gifGrid.model.toItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifGridViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: GifInteractor
) : ViewModel() {

    private val clearList = Channel<Unit>(Channel.CONFLATED)
    private val searchTermFlow = savedStateHandle.getStateFlow(SEARCH_TERM_VALUE, "")
    private val searchTermInputFlow = MutableStateFlow("")
    private val searchTermInputDebouncesFlow = searchTermInputFlow
        .debounce(SEARCH_TIMEOUT)

    init {
        viewModelScope.launch {
            searchTermInputDebouncesFlow.collectLatest {
                savedStateHandle[SEARCH_TERM_VALUE] = it
                clearList.trySend(Unit)
            }
        }
    }

    val gifsFlow = flowOf(
        clearList.receiveAsFlow().map { PagingData.empty() },
        searchTermFlow.flatMapLatest {
            interactor.getPaging(it)
        }.map { pagingData ->
            pagingData.map { it.toItemModel() }
        }.cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun performSearch(searchTerm: String) {
        searchTermInputFlow.value = searchTerm
    }

    private companion object {
        const val SEARCH_TERM_VALUE = "SEARCH_TERM"
        const val SEARCH_TIMEOUT = 300L
    }
}
