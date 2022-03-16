package com.mina.movie.search

import com.mina.common.models.Movie
import com.mina.movies.network.MoviesResponseDto
import com.mina.movies.network.MoviesService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MovieListRepositoryTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private val moviesService: MoviesService = mock()
    private val movieDtoConverter: MovieDtoConverter = mock()

    private val movieListRepository = MovieListRepository(
        moviesService = moviesService,
        movieDtoConverter = movieDtoConverter
    )


    @Test
    fun `should return empty response if movie list is empty`() = runBlockingTest {
        val result: Result<MoviesResponseDto> =
            Result.success(MoviesResponseDto(movies = emptyList()))
        given(moviesService.getMovies(any())).willReturn(result)

        val response: MovieListRepository.MovieListResponse = movieListRepository.searchMovies("")
        assertEquals(response, MovieListRepository.MovieListResponse.Empty)
    }

    @Test
    fun `should return error if service response is failure`() = runBlockingTest {
        val result: Result<MoviesResponseDto> = Result.failure(Exception())
        given(moviesService.getMovies(any())).willReturn(result)

        val response: MovieListRepository.MovieListResponse = movieListRepository
            .searchMovies("")

        assert(response is MovieListRepository.MovieListResponse.Error)
    }

    @Test
    fun `should return success with movie list`() = runBlockingTest {
        val moviesResponseDto: MoviesResponseDto = mock {
            whenever(it.movies).thenReturn(listOf(mock()))
        }
        val result: Result<MoviesResponseDto> = Result.success(moviesResponseDto)
        given(moviesService.getMovies(any())).willReturn(result)

        val movie: Movie = mock()
        given(movieDtoConverter.convert(any(), any())).willReturn(movie)

        val response: MovieListRepository.MovieListResponse = movieListRepository
            .searchMovies("")

        assertEquals(response, MovieListRepository.MovieListResponse.Success(listOf(movie)))
    }
}