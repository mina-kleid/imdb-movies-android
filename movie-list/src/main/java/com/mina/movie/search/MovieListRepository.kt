package com.mina.movie.search


import com.mina.common.models.Movie
import javax.inject.Inject

internal class MovieListRepository @Inject constructor() {

    suspend fun searchMovies(query: String): List<Movie> {
        return listOf(
            Movie("Movie 1", "Director", "1990"),
            Movie("Movie 2", "Director2", "1992")
        )
    }
}