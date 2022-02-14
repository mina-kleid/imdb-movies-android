package com.mina.movies.ui.main.search

import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Empty)
    val viewState: Flow<ViewState> get() = _viewState.filterNotNull()

    private val _viewEvent: Channel<ViewEvent> = Channel(Channel.CONFLATED)
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

    sealed class ViewState {

        data class Content(val movies: List<String>): ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
        object Error : ViewState()
    }

    sealed class ViewEvent {
    }
}