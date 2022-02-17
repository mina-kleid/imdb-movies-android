package com.mina.movie.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mina.common.models.Movie

internal class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie) {
        val movieName: TextView = itemView.findViewById(R.id.movieTitle)
        val movieDetails: TextView = itemView.findViewById(R.id.movieDetails)
        movieName.text = movie.title
        movieDetails.text = "${movie.director} ${movie.year}"
    }
}