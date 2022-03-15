package com.mina.movie.search

import app.cash.turbine.test
import com.mina.common.models.Movie
import com.mina.common.models.MovieJsonConverter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MovieListViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private val movieListRepository: MovieListRepository = mock()
    private val movieJsonConverter: MovieJsonConverter = mock()

    private val viewModel: MovieListViewModel = MovieListViewModel(
        movieListRepository = movieListRepository,
        movieJsonConverter = movieJsonConverter
    )


    @Test
    fun `viewState should emit initial state`() = runBlockingTest {
        viewModel
            .viewState
            .test {
                assertEquals(expectMostRecentItem(), MovieListViewModel.ViewState.Initial)
            }
    }

    @Test
    fun `viewState should emit empty when there are no results`() = runBlockingTest {
        given(movieListRepository.searchMovies(any())).willReturn(MovieListRepository.MovieListResponse.Empty)

        viewModel.performSearch("test")
        viewModel
            .viewState
            .test {
                assertEquals(expectMostRecentItem(), MovieListViewModel.ViewState.Empty)
            }
    }

    @Test
    fun `viewState should emit error when repository returns error`() = runBlockingTest {
        given(movieListRepository.searchMovies(any())).willReturn(
            MovieListRepository.MovieListResponse.Error(
                Exception()
            )
        )

        viewModel.performSearch("test")
        viewModel
            .viewState
            .test {
                assertEquals(expectMostRecentItem(), MovieListViewModel.ViewState.Error)
            }
    }

    @Test
    fun `viewState should emit Loading while loading from repository`() = runBlockingTest {
        viewModel.performSearch("test")

        viewModel
            .viewState
            .test {
                assertEquals(expectMostRecentItem(), MovieListViewModel.ViewState.Loading)
            }
    }

    @Test
    fun `viewState should emit content`() = runBlockingTest {
        val movieList: List<Movie> = mock()
        given(movieListRepository.searchMovies(any()))
            .willReturn(MovieListRepository.MovieListResponse.Success(movieList))

        viewModel.performSearch("test")

        viewModel
            .viewState
            .test {
                assertEquals(
                    expectMostRecentItem(),
                    MovieListViewModel.ViewState.Content(movieList)
                )
            }
    }

    @Test
    fun `viewEvent should emit navigate when movieClicked is called`() = runBlockingTest {
        val movieJson = "movieJson"
        val expectedUriString = "android-app://com.mina.movies/movie_fragment?movie=$movieJson"

        given(movieJsonConverter.toJson(any())).willReturn(movieJson)

        viewModel.movieClicked(mock())

        viewModel
            .viewEvent
            .test {
                assertEquals(
                    expectMostRecentItem(),
                    MovieListViewModel.ViewEvent.Navigate(expectedUriString)
                )
            }
    }
}