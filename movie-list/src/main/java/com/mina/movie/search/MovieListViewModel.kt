package com.mina.movie.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.common.models.Movie
import com.mina.common.models.MovieJsonConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val movieJsonConverter: MovieJsonConverter
) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Initial)
    val viewState: Flow<ViewState> get() = _viewState

    private val _viewEvent: Channel<ViewEvent> = Channel(Channel.CONFLATED)
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

    private var movies: MutableList<Movie> = mutableListOf()

    fun performSearch(query: String?) {
        if (query != null) {
            _viewState.value = ViewState.Loading
            viewModelScope.launch {
                _viewState.value = getMovies(query)
            }
        }
    }

    fun movieClicked(movie: Movie) {
        val movieJson: String = movieJsonConverter.toJson(movie)
        val uriString = "android-app://com.mina.movies/movie_fragment?movie=$movieJson"
        viewModelScope.launch {
            _viewEvent.send(ViewEvent.Navigate(uriString = uriString))
        }
    }

    fun hideMovie(position: Int) {
        val movieToHide: Movie = movies[position]
        movies.removeAt(position)
        viewModelScope.launch {
            movieListUseCase.hideMovie(movieToHide)
            _viewEvent.send(ViewEvent.HideMovie(position = position))
        }
    }

    private suspend fun getMovies(query: String): ViewState =
        when (
            val response: MovieListUseCase.MovieListUseCaseResponse =
                movieListUseCase.searchMovies(query)
        ) {
            is MovieListUseCase.MovieListUseCaseResponse.Success -> {
                movies = response.movies.toMutableList()
                ViewState.Content(movies)
            }
            MovieListUseCase.MovieListUseCaseResponse.Empty -> ViewState.Empty
            is MovieListUseCase.MovieListUseCaseResponse.Error -> ViewState.Error
        }

    sealed class ViewState {

        object Initial : ViewState()
        data class Content(val movies: List<Movie>) : ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
        object Error : ViewState()
    }

    sealed class ViewEvent {
        data class Navigate(val uriString: String) : ViewEvent()
        data class HideMovie(val position: Int) : ViewEvent()
    }
}