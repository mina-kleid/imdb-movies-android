package com.mina.movie

import com.mina.common.models.Movie

internal data class MovieFavorite(
    val movie: Movie,
    val isFavorite: Boolean
)
