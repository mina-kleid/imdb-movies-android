package com.mina.movies.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
public data class MoviesResponseDto(
    @Json(name = "results") val movies: List<MovieDto>
) {

    @JsonClass(generateAdapter = true)
    data class MovieDto(
        @Json(name = "title") val title: String,
        @Json(name= "release_date") val year: String,
        @Json(name = "overview") val description: String,
        @Json(name = "vote_average") val rating: String,
        @Json(name = "poster_path") val posterUrl: String? = null
    )
}