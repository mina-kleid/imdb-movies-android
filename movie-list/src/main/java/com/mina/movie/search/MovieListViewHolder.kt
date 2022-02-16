package com.mina.movie.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mina.common.models.Movie

internal class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie) {
        val movieName: TextView = itemView.findViewById(R.id.movieName)
        movieName.text = movie.title
    }
}