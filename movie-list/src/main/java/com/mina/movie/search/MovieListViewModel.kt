package com.mina.movie.search

import android.net.Uri
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
import java.net.URI
import javax.inject.Inject

@HiltViewModel
internal class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val movieJsonConverter: MovieJsonConverter
) :
    ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Initial)
    val viewState: Flow<ViewState> get() = _viewState

    private val _viewEvent: Channel<ViewEvent> = Channel(Channel.CONFLATED)
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

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
        val uri = Uri.parse("android-app://com.mina.movies/movie_fragment?movie=$movieJson")
        viewModelScope.launch {
            _viewEvent.send(ViewEvent.Navigate(uri = uri))
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

        object Initial : ViewState()
        data class Content(val movies: List<Movie>) : ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
        object Error : ViewState()
    }

    sealed class ViewEvent {
        data class Navigate(val uri: Uri): ViewEvent()
    }
}