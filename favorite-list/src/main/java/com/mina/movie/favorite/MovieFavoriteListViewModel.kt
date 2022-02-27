package com.mina.movie.favorite

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.common.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieFavoriteListViewModel @Inject constructor(private val movieFavoriteListRepository: MovieFavoriteListRepository) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Empty)
    val viewState: Flow<ViewState> get() = _viewState

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadFavorites() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            val movies: List<Movie> = movieFavoriteListRepository.loadFavorites()
            _viewState.value = ViewState.Content(movies)
        }

    }

    sealed class ViewState {
        data class Content(val movies: List<Movie>) : ViewState()
        object Empty : ViewState()
        object Loading : ViewState()
    }

}