package com.mina.movie.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.mina.common.models.Movie
import javax.inject.Inject

public class MovieListAdapter @Inject constructor(
    private val itemClickListener: MovieListItemClickListener,
    private val requestOptions: RequestOptions
) : RecyclerView.Adapter<MovieListViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return MovieListViewHolder(itemView, requestOptions)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(movie = movies[position], itemClickListener = itemClickListener)
    }

    override fun getItemCount(): Int = movies.size

    fun updateAdapter(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        this.notifyDataSetChanged()
    }
}