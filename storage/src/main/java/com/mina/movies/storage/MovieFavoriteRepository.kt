package com.mina.movies.storage

import com.mina.common.models.Movie
import javax.inject.Inject

public interface MovieFavoriteRepository {
    suspend fun isMovieFavorited(movie: Movie): Boolean

    suspend fun setMovieFavorited(movie: Movie)

    suspend fun setMovieUnfavorited(movie: Movie)
}
