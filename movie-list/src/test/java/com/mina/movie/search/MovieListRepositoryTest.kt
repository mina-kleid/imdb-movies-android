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
import org.junit.Assert.assertThrows
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
    fun `should return list of movies`() = runBlockingTest {
        val movie: Movie = mock()
        val moviesResponseDto: MoviesResponseDto = mock {
            whenever(it.movies).thenReturn(listOf(mock()))
        }
        given(moviesService.getMovies(any())).willReturn(Result.success(moviesResponseDto))
        given(movieDtoConverter.convert(any(), any())).willReturn(movie)

        val result = movieListRepository.searchMovies("")

        assertEquals(listOf(movie), result)
    }
}