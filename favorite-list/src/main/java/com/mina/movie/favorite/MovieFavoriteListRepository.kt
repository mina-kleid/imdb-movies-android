package com.mina.movie.favorite

import com.mina.common.models.Movie
import com.mina.movies.storage.MovieFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MovieFavoriteListRepository @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) {
    suspend fun loadFavorites(): Flow<List<Movie>> =
        movieFavoriteRepository.getAllFavoriteMovies()
}