package com.mina.movie.search

import com.mina.common.models.Movie

interface MovieListItemClickListener {
    fun onItemClicked(movie: Movie)
}