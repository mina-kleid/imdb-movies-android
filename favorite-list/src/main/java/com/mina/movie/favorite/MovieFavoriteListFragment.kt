package com.mina.movie.favorite

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mina.common.models.Movie
import com.mina.common.models.MovieJsonConverter
import com.mina.movie.favorite.databinding.MovieFavoriteListFragmentBinding
import com.mina.movie.item.MovieListAdapter
import com.mina.movie.item.MovieListItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MovieFavoriteListFragment : Fragment(), MovieListItemClickListener {

    private lateinit var binding: MovieFavoriteListFragmentBinding
    private val viewModel: MovieFavoriteListViewModel by viewModels()

    @Inject
    internal lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = MovieFavoriteListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(viewModel)

        binding.movieList.adapter = adapter
        binding.movieList.layoutManager = LinearLayoutManager(context);

        observeViewStates()
    }

    private fun observeViewStates() {
        viewModel
            .viewState
            .onEach {
                binding.textView.visibility = View.VISIBLE
                when (it) {
                    is MovieFavoriteListViewModel.ViewState.Content -> showContent(it.movies)
                    MovieFavoriteListViewModel.ViewState.Loading -> binding.textView.text =
                        getString(R.string.movie_favorite_list_loading)
                    MovieFavoriteListViewModel.ViewState.Empty -> binding.textView.text =
                        getString(R.string.movie_favorite_list_empty)
                }
            }.launchIn(lifecycleScope)
    }

    private fun showContent(movies: List<Movie>) {
        binding.textView.visibility = View.GONE
        adapter.updateAdapter(movies)
    }

    override fun onItemClicked(movie: Movie) {
        //https://issuetracker.google.com/issues/148523779?pli=1
        val movieJson: String = MovieJsonConverter.toJson(movie)
        val uri = Uri.parse("android-app://com.mina.movies/movie_fragment?movie=$movieJson")
        findNavController().navigate(uri)
    }
}