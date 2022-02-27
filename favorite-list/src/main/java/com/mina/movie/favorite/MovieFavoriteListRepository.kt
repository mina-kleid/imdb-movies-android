package com.mina.movie.favorite

import com.mina.common.models.Movie
import com.mina.movies.storage.MovieFavoriteRepository
import javax.inject.Inject

internal class MovieFavoriteListRepository @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) {
    suspend fun loadFavorites(): List<Movie> =
        movieFavoriteRepository.getAllFavoriteMovies()
}