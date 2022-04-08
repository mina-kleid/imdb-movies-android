package com.mina.movies.storage

import com.mina.common.models.Movie
import kotlinx.coroutines.flow.Flow

public interface MovieHiddenRepository {

    suspend fun getAllHiddenMovies(): Flow<List<Movie>>

    suspend fun hideMovie(movie: Movie)
}