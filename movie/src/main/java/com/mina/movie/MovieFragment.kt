package com.mina.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mina.common.models.Movie
import com.mina.common.models.MovieJsonConverter
import com.mina.movie.databinding.MovieFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: MovieFragmentBinding

    private val args: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie: Movie = MovieJsonConverter.fromJson(args.movie)

        binding.movieTitle.text = movie.title
    }
}