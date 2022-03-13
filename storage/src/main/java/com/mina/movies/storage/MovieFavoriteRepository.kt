package com.mina.movies.storage

import com.mina.common.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public interface MovieFavoriteRepository {

    suspend fun getAllFavoriteMovies(): Flow<List<Movie>>

    suspend fun isMovieFavorite(movie: Movie): Boolean

    suspend fun addMovieToFavorite(movie: Movie)

    suspend fun removeMovieFromFavorite(movie: Movie)
}
