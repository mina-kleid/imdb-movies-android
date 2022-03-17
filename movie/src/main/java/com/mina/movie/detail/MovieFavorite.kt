package com.mina.movie.detail

import com.mina.common.models.Movie

internal data class MovieFavorite(
    val movie: Movie,
    val isFavorite: Boolean
)
