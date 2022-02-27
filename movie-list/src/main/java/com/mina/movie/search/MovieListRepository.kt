package com.mina.movie.search


import com.mina.common.models.Movie
import javax.inject.Inject

internal class MovieListRepository @Inject constructor() {

    suspend fun searchMovies(query: String): List<Movie> {
        return listOf(
            Movie(
                title = "Movie 1",
                year = "1990",
                rating = "7/10",
                posterUrl = "url",
                description = "description of the movie"
            ),
            Movie(
                title = "Movie 2",
                year = "1992",
                rating = "1/10",
                posterUrl = "url",
                description = "description of the movie"
            ),
        )
    }
}