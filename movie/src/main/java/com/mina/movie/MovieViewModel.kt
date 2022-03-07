package com.mina.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.common.models.Movie
import com.mina.common.models.MovieJsonConverter
import com.mina.movies.storage.MovieFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository,
    private val movieJsonConverter: MovieJsonConverter
) :
    ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val viewState: Flow<ViewState> get() = _viewState

    private var isMovieFavorite: Boolean = false
    private lateinit var movie: Movie

    fun initialize(movieJson: String) {
        this.movie = movieJsonConverter.fromJson(movieJson)
        viewModelScope.launch {
            isMovieFavorite = movieFavoriteRepository.isMovieFavorite(movie)
            _viewState.value = ViewState.Content(
                MovieFavorite(movie = movie, isFavorite = isMovieFavorite)
            )
        }
    }

    fun movieFavoriteToggled() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            isMovieFavorite = if (isMovieFavorite) {
                movieFavoriteRepository.removeMovieFromFavorite(movie = movie)
                false
            } else {
                movieFavoriteRepository.addMovieToFavorite(movie = movie)
                true
            }
            _viewState.value =
                ViewState.Content(MovieFavorite(movie = movie, isFavorite = isMovieFavorite))
        }

    }

    sealed class ViewState {
        data class Content(val movieFavorite: MovieFavorite) : ViewState()
        object Loading : ViewState()
    }
}