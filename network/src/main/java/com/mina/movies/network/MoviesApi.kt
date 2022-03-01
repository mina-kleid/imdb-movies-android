package com.mina.movies.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MoviesApi {
    @GET("search/movie")
    suspend fun movies(@Query("query") query: String): Response<MoviesResponseDto>
}