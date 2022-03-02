package com.mina.movies.network

data class MoviesApiException(override val message: String?) : Exception()