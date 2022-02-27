package com.mina.movie.item

import com.mina.common.models.Movie

public interface MovieListItemClickListener {
    fun onItemClicked(movie: Movie)
}