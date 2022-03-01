package com.mina.movie.search

import com.mina.common.models.Movie
import com.mina.movies.network.MoviesResponseDto
import javax.inject.Inject

internal class MovieDtoConverter @Inject constructor() {

    fun convert(movieDto: MoviesResponseDto.MovieDto): Movie =
        Movie(
            title = movieDto.title,
            description = movieDto.description,
            posterUrl = movieDto.posterUrl,
            year = movieDto.year,
            rating = movieDto.rating
        )
}