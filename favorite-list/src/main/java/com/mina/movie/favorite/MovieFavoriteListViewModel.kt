package com.mina.movie.favorite

import android.net.Uri
import androidx.lifecycle.*
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
internal class MovieFavoriteListViewModel @Inject constructor(
    private val movieFavoriteListRepository: MovieFavoriteListRepository,
    private val movieJsonConverter: MovieJsonConverter
    ) :
    ViewModel(),
    LifecycleObserver {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Empty)
    val viewState: Flow<ViewState> get() = _viewState

    private val _viewEvent: Channel<ViewEvent> = Channel(Channel.CONFLATED)
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadFavorites() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            val movies: List<Movie> = movieFavoriteListRepository.loadFavorites()
            if (movies.isNotEmpty()) {
                _viewState.value = ViewState.Content(movies)
            } else {
                _viewState.value = ViewState.Empty
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

    sealed class ViewState {
        data class Content(val movies: List<Movie>) : ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
    }

    sealed class ViewEvent {
        data class Navigate(val uri: Uri): ViewEvent()
    }
}