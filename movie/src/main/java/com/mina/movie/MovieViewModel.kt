package com.mina.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.common.models.Movie
import com.mina.movies.storage.MovieFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(private val movieFavoriteRepository: MovieFavoriteRepository) :
    ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val viewState: Flow<ViewState> get() = _viewState

    private var isMovieFavorited: Boolean = false
    private lateinit var movie: Movie

    fun initialize(movie: Movie) {
        this.movie = movie
        viewModelScope.launch {
            isMovieFavorited = movieFavoriteRepository.isMovieFavorited(movie)
            _viewState.value = ViewState.Content(isFavorite = isMovieFavorited)
        }
    }

    fun movieFavoriteToggled() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            isMovieFavorited = if (isMovieFavorited) {
                movieFavoriteRepository.setMovieUnfavorited(movie = movie)
                false
            } else {
                movieFavoriteRepository.setMovieFavorited(movie = movie)
                true
            }
            _viewState.value = ViewState.Content(isFavorite = isMovieFavorited)
        }

    }

    sealed class ViewState {
        data class Content(val isFavorite: Boolean) : ViewState()
        object Loading : ViewState()
    }
}