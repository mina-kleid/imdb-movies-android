package com.mina.movie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mina.movie.databinding.MovieFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
internal class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: MovieFragmentBinding

    private val args: MovieFragmentArgs by navArgs()

    private var favoriteMenuItem: MenuItem? = null
    private var loadingMenuItem: MenuItem? = null

    @Inject
    lateinit var requestOptions: RequestOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initialize(args.movie)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observeViewState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        favoriteMenuItem = menu.findItem(R.id.action_favorite)
        loadingMenuItem = menu.findItem(R.id.action_loading)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                viewModel.movieFavoriteToggled()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun observeViewState() {
        viewModel
            .viewState
            .onEach {
                when (it) {
                    is MovieViewModel.ViewState.Content -> {
                        favoriteMenuItem?.isVisible = true
                        loadingMenuItem?.isVisible = false
                        update(movieFavorite = it.movieFavorite)
                    }
                    MovieViewModel.ViewState.Loading -> {
                        favoriteMenuItem?.isVisible = false
                        loadingMenuItem?.isVisible = true
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun update(movieFavorite: MovieFavorite) {
        updateFavoriteMenuItem(movieFavorite.isFavorite)
        with(movieFavorite.movie) {
            (requireActivity() as AppCompatActivity)
                .supportActionBar
                ?.title = this.title

            Glide
                .with(this@MovieFragment)
                .load(this.posterUrl)
                .apply(requestOptions)
                .into(binding.moviePoster)

            binding.description.text = this.description
            binding.rating.text = this.rating
        }
    }

    private fun updateFavoriteMenuItem(isFavorite: Boolean) {
        val icon: Drawable? = if (isFavorite) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favorite)
        }
        favoriteMenuItem?.icon = icon
    }
}