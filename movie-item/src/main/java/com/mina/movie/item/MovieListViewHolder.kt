package com.mina.movie.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mina.common.models.Movie

public class MovieListViewHolder(
    itemView: View,
    private val requestOptions: RequestOptions
) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie, itemClickListener: MovieListItemClickListener) {
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val movieDescription: TextView = itemView.findViewById(R.id.movieDescription)
        val movieRating: TextView = itemView.findViewById(R.id.movieRating)
        val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        movieTitle.text = movie.title
        movieDescription.text = movie.description
        movieRating.text = movie.rating
        movie.posterUrl?.let {
            Glide
                .with(itemView.context)
                .load(it)
                .apply(requestOptions)
                .into(moviePoster)
        }
        itemView.setOnClickListener { itemClickListener.onItemClicked(movie) }
    }
}