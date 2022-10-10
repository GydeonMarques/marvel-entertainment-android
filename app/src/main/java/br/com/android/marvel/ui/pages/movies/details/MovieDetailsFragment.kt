package br.com.android.marvel.ui.pages.movies.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.android.marvel.di.factory.ViewModelFactory
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.entertainment.databinding.FragmentMovieDetailsBinding
import br.com.android.marvel.singleton.MyApplication
import br.com.android.marvel.utils.LoadImageUtils
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args by navArgs<MovieDetailsFragmentArgs>()

    private lateinit var binding: FragmentMovieDetailsBinding

    private val navController by lazy { findNavController() }

    private val viewModel by viewModels<MovieDetailsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMovieDetailsBinding.inflate(inflater, container, false).run {
            lifecycleOwner = this.lifecycleOwner
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewDataBind()
        setViewActions()
    }

    private fun setViewActions() {
        with(binding) {
            toolbarContainer.toolbar.run {
                setNavigationOnClickListener { navController.navigateUp() }
            }
        }
    }

    private fun setupViewDataBind() {
        with(binding) {
            with(viewModel) {
                Timber.i("Movie ID: %s", args.movieId)
                getMovieById(args.movieId).observe(viewLifecycleOwner) { movie ->
                    movie?.let {
                        toolbarContainer.toolbar.title = movie.title
                        binding.movie = movie
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}