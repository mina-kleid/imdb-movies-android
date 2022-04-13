package com.mina.movie.search

import com.mina.common.models.Movie
import com.mina.movies.network.MoviesService
import com.mina.movies.storage.MovieHideRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

internal class MovieListUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val movieHideRepository: MovieHideRepository
) {

    suspend fun searchMovies(query: String): MovieListUseCaseResponse {
        try {
            val movieList: List<Movie> = movieListRepository.searchMovies(query = query)
            val hiddenMovies: List<Movie> = movieHideRepository
                .getAllHiddenMovies()
                .firstOrNull()
                ?: emptyList()
            val filteredMovies: List<Movie> = movieList - hiddenMovies

            if (filteredMovies.isEmpty()) {
                return MovieListUseCaseResponse.Empty
            }

            return MovieListUseCaseResponse.Success(filteredMovies)
        } catch (e: Exception) {
            return MovieListUseCaseResponse.Error(e)
        }
    }

    suspend fun hideMovie(movie: Movie) {
        movieHideRepository.hideMovie(movie)
    }

    sealed class MovieListUseCaseResponse {

        data class Success(val movies: List<Movie>) : MovieListUseCaseResponse()
        object Empty : MovieListUseCaseResponse()
        data class Error(val exception: Exception) : MovieListUseCaseResponse()
    }
}