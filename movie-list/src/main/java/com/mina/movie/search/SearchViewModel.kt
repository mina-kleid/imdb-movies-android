package com.mina.movie.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.common.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(private val movieListRepository: MovieListRepository) :
    ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Empty)
    val viewState: Flow<ViewState> get() = _viewState

    private val _viewEvent: Channel<ViewEvent> = Channel(Channel.CONFLATED)
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

    fun performSearch(query: String?) {
        if (query != null) {
            _viewState.value = ViewState.Loading
            viewModelScope.launch {
                val movies: List<Movie> = movieListRepository.searchMovies(query)
                _viewState.value = ViewState.Content(movies)
            }
        }
    }

    sealed class ViewState {

        data class Content(val movies: List<Movie>) : ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
        object Error : ViewState()
    }

    sealed class ViewEvent {
    }
}