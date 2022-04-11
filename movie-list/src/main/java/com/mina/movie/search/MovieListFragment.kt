package com.mina.movie.search

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mina.common.models.Movie
import com.mina.movie.item.MovieListAdapter
import com.mina.movie.item.MovieListItemClickListener
import com.mina.movie.item.MovieListItemSwipeListener
import com.mina.movie.item.RecyclerViewSwipeHandler
import com.mina.movie.search.databinding.MovieListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment :
    Fragment(),
    SearchView.OnQueryTextListener,
    MovieListItemClickListener,
    MovieListItemSwipeListener {

    private lateinit var binding: MovieListFragmentBinding
    private val viewModel: MovieListViewModel by viewModels()

    @Inject
    internal lateinit var adapter: MovieListAdapter

    @Inject
    internal lateinit var swipeCallback: RecyclerViewSwipeHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewStates()
        observeViewEvents()
        with(binding) {
            searchView.isSubmitButtonEnabled = true
            searchView.setOnQueryTextListener(this@MovieListFragment)
            movieList.adapter = adapter
            movieList.layoutManager = LinearLayoutManager(context)
            ItemTouchHelper(swipeCallback).attachToRecyclerView(movieList)
        }
    }

    private fun observeViewStates() {
        viewModel
            .viewState
            .onEach {
                binding.textView.visibility = VISIBLE
                binding.searchView.isSubmitButtonEnabled = true
                when (it) {
                    is MovieListViewModel.ViewState.Content -> showContent(it.movies)
                    MovieListViewModel.ViewState.Loading -> {
                        binding.searchView.isSubmitButtonEnabled = false
                        binding.textView.text = getString(R.string.movie_list_loading)
                    }
                    MovieListViewModel.ViewState.Error -> binding.textView.text =
                        getString(R.string.movie_list_error)
                    MovieListViewModel.ViewState.Empty -> binding.textView.text =
                        getString(R.string.movie_list_empty)
                    MovieListViewModel.ViewState.Initial -> binding.textView.text =
                        getString(R.string.movie_list_Initial)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeViewEvents() {
        viewModel
            .viewEvent
            .onEach {
                when (it) {
                    is MovieListViewModel.ViewEvent.Navigate -> {
                        val uri = Uri.parse(it.uriString)
                        findNavController().navigate(uri)
                    }
                    is MovieListViewModel.ViewEvent.HideMovie ->
                        adapter.hideItem(it.position)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showContent(movies: List<Movie>) {
        binding.textView.visibility = GONE
        adapter.updateAdapter(movies)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.performSearch(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onItemClicked(movie: Movie) {
        viewModel.movieClicked(movie = movie)
    }

    override fun movieItemSwipedLeft(viewHolder: RecyclerView.ViewHolder) {
        viewModel.hideMovie(viewHolder.adapterPosition)
    }
}