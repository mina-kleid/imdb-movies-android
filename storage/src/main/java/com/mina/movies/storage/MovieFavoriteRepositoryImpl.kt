package com.mina.movies.storage

import com.mina.common.models.Movie
import javax.inject.Inject

internal class MovieFavoriteRepositoryImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieFavoriteRepository {

    override suspend fun isMovieFavorited(movie: Movie): Boolean =
        movieDao.getMovie(title = movie.title, year = movie.year) != null

    override suspend fun setMovieFavorited(movie: Movie) {
        val movieEntity: MovieEntity = MovieEntityConverter.convertToMovieEntity(movie = movie)
        movieDao.updateOrInsert(movieEntity)
    }

    override suspend fun setMovieUnfavorited(movie: Movie) {
        val movieEntity: MovieEntity = MovieEntityConverter.convertToMovieEntity(movie = movie)
        movieDao.delete(movieEntity)
    }
}