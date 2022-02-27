package com.mina.movies.storage

import com.mina.common.models.Movie
import javax.inject.Inject

public interface MovieFavoriteRepository {

    suspend fun getAllFavoriteMovies(): List<Movie>

    suspend fun isMovieFavorite(movie: Movie): Boolean

    suspend fun addMovieToFavorite(movie: Movie)

    suspend fun removeMovieFromFavorite(movie: Movie)
}
