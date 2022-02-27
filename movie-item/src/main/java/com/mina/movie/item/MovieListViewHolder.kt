package com.mina.movie.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mina.common.models.Movie

public class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie, itemClickListener: MovieListItemClickListener) {
        val movieName: TextView = itemView.findViewById(R.id.movieTitle)
        val movieDetails: TextView = itemView.findViewById(R.id.movieDetails)
        movieName.text = movie.title
        movieDetails.text = "${movie.rating} ${movie.year}"
        itemView.setOnClickListener { itemClickListener.onItemClicked(movie) }
    }
}