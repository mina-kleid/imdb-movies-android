package com.mina.movie.search


import com.mina.common.models.Movie
import com.mina.movies.network.MoviesResponseDto
import com.mina.movies.network.MoviesService
import javax.inject.Inject

internal class MovieListRepository @Inject constructor(
    private val moviesService: MoviesService,
    private val movieDtoConverter: MovieDtoConverter
) {

    suspend fun searchMovies(query: String): MovieListResponse {
        try {
            val movieResult: Result<MoviesResponseDto> = moviesService.getMovies(query = query)
            if (movieResult.isSuccess) {
                val movieDtoList: List<MoviesResponseDto.MovieDto> = movieResult.getOrThrow().movies
                if (movieDtoList.isEmpty()) {
                    return MovieListResponse.Empty
                }
                val movieList: List<Movie> = movieDtoList
                    .map { movieDtoConverter.convert(it, MoviesService.POSTER_IMAGE_BASE_URL) }
                return MovieListResponse.Success(movieList)
            } else {
                throw Exception("Unknown")
            }
        } catch (e: Exception) {
            return MovieListResponse.Error(e)
        }
    }

    sealed class MovieListResponse {

        data class Success(val movies: List<Movie>) : MovieListResponse()
        object Empty : MovieListResponse()
        data class Error(val exception: Exception) : MovieListResponse()
    }
}