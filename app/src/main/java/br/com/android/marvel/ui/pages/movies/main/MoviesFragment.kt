package br.com.android.marvel.ui.pages.movies.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.android.marvel.di.factory.ViewModelFactory
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.entertainment.databinding.FragmentMoviesBinding
import br.com.android.marvel.entertainment.databinding.LayoutDialogRateMoviesBinding
import br.com.android.marvel.singleton.MyApplication
import br.com.android.marvel.ui.pages.movies.adapter.MoviesAdapter
import br.com.gms.domain.entity.MovieEntity
import br.com.gms.storage.sharedPrefs.LocalSharedPreference
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MoviesFragment : Fragment() {

    private var filterByGenre: String? = null

    @Inject
    lateinit var storage: LocalSharedPreference

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var movieAdapter: MoviesAdapter
    private lateinit var binding: FragmentMoviesBinding

    private val navController by lazy { findNavController() }

    private val viewModel by viewModels<MoviesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMoviesBinding.inflate(inflater, container, false).run {
            lifecycleOwner = this.lifecycleOwner
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupActions()
        getAllMovies()
    }

    private fun setupViews() {
        with(binding.layoutMovies) {
            with(viewModel) {

                filterByGenre = storage.getString(LocalSharedPreference.Key.FILTER_BY_GENRE) ?: getString(R.string.all)
                textViewCaptionTitle.text = getString(R.string.genre_)
                textViewCaptionDescription.text = filterByGenre

                textInputSearchMovies.doOnTextChanged { text, _, _, _ ->
                    if (text.isNullOrEmpty()) {
                        textViewCaptionTitle.text = getString(R.string.genre_)
                        textViewCaptionDescription.text = filterByGenre
                        filterMoviesByTitleOrGenre(genre = filterByGenre)
                    } else {
                        filterMoviesByTitleOrGenre(title = text.toString(), genre = filterByGenre)
                        textViewCaptionTitle.text = getString(R.string.results_for)
                        textViewCaptionDescription.text = text
                    }
                }

                movieAdapter = MoviesAdapter(object : MoviesAdapter.OnItemMovieClickListener {
                    override fun onClick(movie: MovieEntity) {
                        navController.navigate(
                            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                movie.id
                            )
                        )
                    }
                })

                recyclerViewMovies.apply {
                    adapter = movieAdapter
                    setHasFixedSize(true)
                }
            }
        }
    }

    private fun setupActions() {
        with(binding) {

            layoutMovies.toolbarButtonFilter.setOnClickListener { showDialogSortMoviesByGenre() }

            swipeRefreshLayout.apply {
                setColorSchemeResources(
                    R.color.refresh_progress_01,
                    R.color.refresh_progress_02,
                    R.color.refresh_progress_03
                )
                setOnRefreshListener {
                    getAllMovies()
                }
            }
        }
    }

    private fun getAllMovies() {
        ReactiveNetwork.observeNetworkConnectivity(context)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { isConnected ->
                viewModel.getAllMovies(isConnected.available())
                viewModel.state.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is MoviesState.Loading -> {
                            changeLayoutVisibility(isLoading = true)
                        }
                        is MoviesState.Success -> {
                            filterMoviesByTitleOrGenre(genre = filterByGenre)
                            changeLayoutVisibility(isEmpty = state.data.isEmpty())
                        }
                        is MoviesState.OnSearch -> {
                            movieAdapter.submitList(state.data)
                            changeLayoutVisibility(isEmpty = state.data.isEmpty(), isSearch = true)
                        }
                        is MoviesState.Failed -> {
                            changeLayoutVisibility(isError = true)
                        }
                    }
                }
                if (isConnected.available().not()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.could_not_get_the_list_of_movies),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun showDialogSortMoviesByGenre() {
        try {

            val view = LayoutDialogRateMoviesBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext()).run {
                setView(view.root)
                setCancelable(true)
                create()
            }

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

            with(view) {

                when (filterByGenre) {
                    getString(R.string.all) -> radioButtonAll.isChecked = true
                    getString(R.string.drama) -> radioButtonDrama.isChecked = true
                    getString(R.string.sci_fi) -> radioButtonSciFi.isChecked = true
                    getString(R.string.comedy) -> radioButtonComedy.isChecked = true
                    getString(R.string.action) -> radioButtonAction.isChecked = true
                    getString(R.string.thriller) -> radioButtonThriller.isChecked = true
                    getString(R.string.adventure) -> radioButtonAdventure.isChecked = true
                }

                radioGroupMovies.setOnCheckedChangeListener { _, radioButtonSelectedId ->
                    when (radioButtonSelectedId) {
                        R.id.radio_button_all -> filterByGenre = getString(R.string.all)
                        R.id.radio_button_drama -> filterByGenre = getString(R.string.drama)
                        R.id.radio_button_action -> filterByGenre = getString(R.string.action)
                        R.id.radio_button_sci_fi -> filterByGenre = getString(R.string.sci_fi)
                        R.id.radio_button_comedy -> filterByGenre = getString(R.string.comedy)
                        R.id.radio_button_thriller -> filterByGenre = getString(R.string.thriller)
                        R.id.radio_button_adventure -> filterByGenre = getString(R.string.adventure)
                    }
                }

                buttonDialogOk.setOnClickListener {
                    binding.layoutMovies.textViewCaptionTitle.text = getString(R.string.genre_)
                    binding.layoutMovies.textViewCaptionDescription.text = filterByGenre
                    storage.putString(LocalSharedPreference.FILTER_BY_GENRE, filterByGenre!!)
                    filterMoviesByTitleOrGenre(genre = filterByGenre)
                    dialog.dismiss()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun filterMoviesByTitleOrGenre(title: String? = null, genre: String? = null) {
        viewModel.filterMoviesByTitleOrGenre(title, genre)
    }

    private fun changeLayoutVisibility(
        isLoading: Boolean = false,
        isError: Boolean = false,
        isEmpty: Boolean = false,
        isSearch: Boolean = false,
    ) {
        with(binding) {
            swipeRefreshLayout.isRefreshing = false
            layoutError.root.isVisible = isError
            layoutLoading.root.isVisible = isLoading
            layoutNoMovies.root.isVisible = isEmpty && !isSearch
            layoutMovies.root.isVisible = !isLoading && !isError
            layoutMovies.recyclerViewMovies.isVisible = !isEmpty
            layoutMovies.layoutSearchEmpty.root.isVisible = isEmpty && isSearch
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}