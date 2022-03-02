package com.mina.movies.network

import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

public class MoviesService @Inject constructor(
    private val retrofit: Retrofit
) {

    suspend fun getMovies(query: String): Result<MoviesResponseDto> {
        val movieApi = retrofit.create(MoviesApi::class.java)
        return try {
            val response: Response<MoviesResponseDto> = movieApi.movies(query = query)
            val body: MoviesResponseDto? = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                throw MoviesApiException("Unsuccessful response")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}