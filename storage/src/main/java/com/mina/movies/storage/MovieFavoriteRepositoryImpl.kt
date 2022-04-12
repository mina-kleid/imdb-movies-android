package com.mina.movies.storage

import com.mina.common.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MovieFavoriteRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieEntityConverter: MovieEntityConverter
) : MovieFavoriteRepository {

    override suspend fun getAllFavoriteMovies(): Flow<List<Movie>> =
        movieDao
            .getFavoriteMovies()
            .map {
                it.map { movieEntity -> movieEntityConverter.convertToMovie(movieEntity) }
            }

    override suspend fun isMovieFavorite(movie: Movie): Boolean =
        movieDao.getMovie(title = movie.title, year = movie.year) != null

    override suspend fun addMovieToFavorite(movie: Movie) {
        val movieEntity: MovieEntity = movieEntityConverter
            .convertToMovieEntity(movie = movie)
            .copy(isFavorite = true)

        movieDao.insert(movieEntity)
    }

    override suspend fun removeMovieFromFavorite(movie: Movie) {
        val movieEntity: MovieEntity = movieEntityConverter.convertToMovieEntity(movie = movie)
        movieDao.delete(movieEntity)
    }
}