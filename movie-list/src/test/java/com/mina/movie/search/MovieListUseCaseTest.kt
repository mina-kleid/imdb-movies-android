package com.mina.movie.search

import com.mina.common.models.Movie
import com.mina.movies.storage.MovieHideRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MovieListUseCaseTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private val movieListRepository: MovieListRepository = mock()
    private val movieHideRepository: MovieHideRepository = mock()

    private val movieListUseCase = MovieListUseCase(
        movieListRepository = movieListRepository,
        movieHideRepository = movieHideRepository
    )


    @Test
    fun `should return empty if movie list is empty`() = runBlockingTest {
        given(movieListRepository.searchMovies(any())).willReturn(emptyList())
        given(movieHideRepository.getAllHiddenMovies()).willReturn(emptyFlow())

        val response: MovieListUseCase.MovieListUseCaseResponse = movieListUseCase.searchMovies("")

        assertEquals(response, MovieListUseCase.MovieListUseCaseResponse.Empty)
    }

    @Test
    fun `should return error when repository return exception`() = runBlockingTest {
        val exception = MovieListRepositoryException("Test exception")
        given(movieListRepository.searchMovies(any())).willAnswer { throw exception }

        val response: MovieListUseCase.MovieListUseCaseResponse = movieListUseCase.searchMovies("")

        assertEquals(response, MovieListUseCase.MovieListUseCaseResponse.Error(exception))
    }

    @Test
    fun `should return success with movie list`() = runBlockingTest {
        val movie: Movie = mock()
        given(movieListRepository.searchMovies(any())).willReturn(listOf(movie))
        given(movieHideRepository.getAllHiddenMovies()).willReturn(emptyFlow())

        val response: MovieListUseCase.MovieListUseCaseResponse = movieListUseCase.searchMovies("")

        assertEquals(response, MovieListUseCase.MovieListUseCaseResponse.Success(listOf(movie)))
    }

    @Test
    fun `should return empty when hidden movies are same as api movies`() = runBlockingTest {
        val movie: Movie = mock()
        given(movieListRepository.searchMovies(any())).willReturn(listOf(movie))
        given(movieHideRepository.getAllHiddenMovies()).willReturn(flowOf(listOf(movie)))

        val response: MovieListUseCase.MovieListUseCaseResponse = movieListUseCase.searchMovies("")

        assertEquals(response, MovieListUseCase.MovieListUseCaseResponse.Empty)
    }

    @Test
    fun `should return success with hidden movies filtered out`() = runBlockingTest {
        val movie: Movie = mock()
        val movieToHide: Movie = mock()
        given(movieListRepository.searchMovies(any())).willReturn(listOf(movie, movieToHide))
        given(movieHideRepository.getAllHiddenMovies()).willReturn(flowOf(listOf(movieToHide)))

        val response: MovieListUseCase.MovieListUseCaseResponse = movieListUseCase.searchMovies("")

        assertEquals(response, MovieListUseCase.MovieListUseCaseResponse.Success(listOf(movie)))
    }
}