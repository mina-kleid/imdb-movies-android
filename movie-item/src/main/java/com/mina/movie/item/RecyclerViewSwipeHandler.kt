package com.mina.movie.item

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class RecyclerViewSwipeHandler @Inject constructor(private val movieListItemSwipeListener: MovieListItemSwipeListener): ItemTouchHelper.SimpleCallback(0, LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        movieListItemSwipeListener.movieItemSwipedLeft(viewHolder)
    }
}