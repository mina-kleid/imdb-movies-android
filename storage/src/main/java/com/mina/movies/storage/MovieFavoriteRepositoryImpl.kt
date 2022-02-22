package com.mina.movies.storage

import com.mina.common.models.Movie
import javax.inject.Inject

internal class MovieFavoriteRepositoryImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieFavoriteRepository {

    override suspend fun isMovieFavorited(movie: Movie): Boolean {
        return true
    }

    override suspend fun setMovieFavorited(movie: Movie) {

    }

    override suspend fun setMovieUnfavorited(movie: Movie) {

    }
}