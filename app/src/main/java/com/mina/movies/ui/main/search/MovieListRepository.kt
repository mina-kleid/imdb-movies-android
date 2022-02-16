package com.mina.movies.ui.main.search

import com.mina.movies.model.Movie
import javax.inject.Inject

internal class MovieListRepository @Inject constructor() {

    suspend fun searchMovies(query: String): List<Movie> {
        return listOf(Movie("Movie 1"), Movie("Movie 2"))
    }
}