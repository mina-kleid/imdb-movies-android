package com.mina.movies.ui.main.search

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mina.movies.R
import com.mina.movies.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewStates()
        observeViewEvents()
    }

    private fun observeViewEvents() {

    }

    private fun observeViewStates() {
        viewModel
            .viewState
            .onEach {
                binding.textView.visibility = VISIBLE
                when (it) {
                    is SearchViewModel.ViewState.Content -> showContent(it.movies)
                    SearchViewModel.ViewState.Loading -> binding.textView.text = getString(R.string.movie_list_loading)
                    SearchViewModel.ViewState.Error -> binding.textView.text = getString(R.string.movie_list_error)
                    SearchViewModel.ViewState.Empty -> binding.textView.text = getString(R.string.movie_list_empty)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showContent(movies: List<String>) {
        binding.textView.visibility = GONE
    }
}