package com.mina.movie.favorite

import com.mina.common.models.Movie
import javax.inject.Inject

internal class MovieFavoriteListRepository @Inject constructor() {
    fun loadFavorites(): List<Movie> {
        return emptyList()
    }
}