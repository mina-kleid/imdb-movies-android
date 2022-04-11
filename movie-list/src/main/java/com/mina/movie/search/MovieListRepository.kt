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
    private val movieDtoConverter: MovieDtoConverter,
    private val movieHideRepository: MovieHideRepository
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
                val hiddenMovies: List<Movie> = movieHideRepository.getAllHiddenMovies().firstOrNull() ?: emptyList()
                val filteredMovies: List<Movie> = movieList - hiddenMovies
                return MovieListResponse.Success(filteredMovies)
            } else {
                throw Exception("Unknown")
            }
        } catch (e: Exception) {
            return MovieListResponse.Error(e)
        }
    }

    suspend fun hideMovie(movie: Movie) {
        movieHideRepository.hideMovie(movie)
    }

    sealed class MovieListResponse {

        data class Success(val movies: List<Movie>) : MovieListResponse()
        object Empty : MovieListResponse()
        data class Error(val exception: Exception) : MovieListResponse()
    }
}