package com.mina.movies.storage

import com.mina.common.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MovieHideRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieEntityConverter: MovieEntityConverter
) : MovieHideRepository {

    override suspend fun getAllHiddenMovies(): Flow<List<Movie>> =
        movieDao
            .getHiddenMovies()
            .map {
                it.map { movieEntity -> movieEntityConverter.convertToMovie(movieEntity) }
            }

    override suspend fun hideMovie(movie: Movie) {
        val movieEntity: MovieEntity = movieEntityConverter
            .convertToMovieEntity(movie = movie)
            .copy(isHidden = true)

        movieDao.updateOrInsert(movieEntity)
    }
}