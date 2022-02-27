package com.mina.movie.favorite

import androidx.lifecycle.*
import com.mina.common.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieFavoriteListViewModel @Inject constructor(private val movieFavoriteListRepository: MovieFavoriteListRepository) :
    ViewModel(),
    LifecycleObserver {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Empty)
    val viewState: Flow<ViewState> get() = _viewState

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

    sealed class ViewState {
        data class Content(val movies: List<Movie>) : ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
    }

}