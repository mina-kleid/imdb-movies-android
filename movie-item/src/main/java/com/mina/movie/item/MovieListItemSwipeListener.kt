package com.mina.movie.item

import androidx.recyclerview.widget.RecyclerView

public interface MovieListItemSwipeListener {
    fun movieItemSwipedLeft(viewHolder: RecyclerView.ViewHolder)
}