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
internal class MovieListViewModel @Inject constructor(private val movieListRepository: MovieListRepository) :
    ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Initial)
    val viewState: Flow<ViewState> get() = _viewState

    fun performSearch(query: String?) {
        if (query != null) {
            _viewState.value = ViewState.Loading
            viewModelScope.launch {
                _viewState.value = getMovies(query)
            }
        }
    }

    private suspend fun getMovies(query: String): ViewState =
        when (
            val response: MovieListRepository.MovieListResponse =
                movieListRepository.searchMovies(query)
        ) {
            is MovieListRepository.MovieListResponse.Success -> ViewState.Content(response.movies)
            MovieListRepository.MovieListResponse.Empty -> ViewState.Empty
            is MovieListRepository.MovieListResponse.Error -> ViewState.Error
        }

    sealed class ViewState {

        object Initial: ViewState()
        data class Content(val movies: List<Movie>) : ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
        object Error : ViewState()
    }
}