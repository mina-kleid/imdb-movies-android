package com.mina.movies.storage

import com.mina.common.models.Movie
import javax.inject.Inject

internal class MovieFavoriteRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieFavoriteRepository {

    override suspend fun getAllFavoriteMovies(): List<Movie> =
        movieDao.getFavoriteMovies()
            .map { MovieEntityConverter.convertToMovie(it) }

    override suspend fun isMovieFavorite(movie: Movie): Boolean =
        movieDao.getMovie(title = movie.title, year = movie.year) != null

    override suspend fun addMovieToFavorite(movie: Movie) {
        val movieEntity: MovieEntity = MovieEntityConverter.convertToMovieEntity(movie = movie)
        movieDao.updateOrInsert(movieEntity)
    }

    override suspend fun removeMovieFromFavorite(movie: Movie) {
        val movieEntity: MovieEntity = MovieEntityConverter.convertToMovieEntity(movie = movie)
        movieDao.delete(movieEntity)
    }
}