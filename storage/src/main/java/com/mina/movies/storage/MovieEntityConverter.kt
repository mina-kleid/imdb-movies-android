package com.mina.movies.storage

import com.mina.common.models.Movie

internal object MovieEntityConverter {

    fun convertToMovieEntity(movie: Movie): MovieEntity =
        MovieEntity(title = movie.title, year = movie.year)
}