package com.mina.movies.storage

import com.mina.common.models.Movie

internal object MovieEntityConverter {

    fun convertToMovieEntity(movie: Movie): MovieEntity =
        MovieEntity(
            title = movie.title,
            year = movie.year,
            description = movie.description,
            rating = movie.rating,
            posterUrl = movie.posterUrl
        )

    fun convertToMovie(movieEntity: MovieEntity) =
        Movie(
            title = movieEntity.title,
            year = movieEntity.year,
            description = movieEntity.description,
            rating = movieEntity.rating,
            posterUrl = movieEntity.posterUrl
        )
}