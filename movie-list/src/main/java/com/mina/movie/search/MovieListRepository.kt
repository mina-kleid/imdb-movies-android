package com.mina.movie.search


import com.mina.common.models.Movie
import com.mina.movies.network.MoviesResponseDto
import com.mina.movies.network.MoviesService
import com.mina.movies.storage.MovieHideRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import javax.inject.Inject

internal class MovieListRepository @Inject constructor(
    private val moviesService: MoviesService,
    private val movieDtoConverter: MovieDtoConverter
) {

    suspend fun searchMovies(query: String): List<Movie> {
        val movieResult: Result<MoviesResponseDto> = moviesService.getMovies(query = query)
        if (movieResult.isSuccess) {
            return movieResult
                .getOrThrow()
                .movies
                .map { movieDtoConverter.convert(it, MoviesService.POSTER_IMAGE_BASE_URL) }
        } else {
            throw MovieListRepositoryException("Get movies failed")
        }
    }
}