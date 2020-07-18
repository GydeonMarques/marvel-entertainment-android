package br.com.android.marvel.ui.movies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.android.marvel.data.dao.MoviesDatabase
import br.com.android.marvel.data.model.Movie
import br.com.android.marvel.data.repository.MoviesRepository
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.ui.adapter.MoviesAdapter
import br.com.android.marvel.ui.base.BaseActivity
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_moveis.*
import kotlinx.android.synthetic.main.layout_dialog_rate_movies.view.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_no_movies.*
import kotlinx.android.synthetic.main.layout_server_error.*
import java.util.*

class MoviesActivity : BaseActivity() {

    private var filterByGenre: String? =  null

    private lateinit var movies: List<Movie>
    private lateinit var movieAdapter: MoviesAdapter
    private lateinit var moviesViewModel: MoviesViewModel

    companion object{
        private const val FILTER_BY_GENRE = "FILTER_BY_GENRE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moveis)

        //Change layout visibility
        changeLayoutVisibility(isLoading = true)

        //================================================================================================================================//

        //User-specified rating saved in preferences
        filterByGenre = Hawk.get<String>(FILTER_BY_GENRE, getString(R.string.all))
        text_view_caption_title?.text = getString(R.string.genre_)
        text_view_caption_description?.text = filterByGenre

        //================================================================================================================================//

        swipe_refresh_layout?.setColorSchemeResources(R.color.refresh_progress_01, R.color.refresh_progress_02, R.color.refresh_progress_03)
        swipe_refresh_layout?.setOnRefreshListener { moviesViewModel.getMovies() }

        //================================================================================================================================//

        image_button_filter?.setOnClickListener {showDialogSortMoviesByGenre()}

        //================================================================================================================================//

        movieAdapter = MoviesAdapter { movie -> startActivity(MovieDetailsActivity.getIntent(this, movie)) }

        recycler_view_movies?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view_movies?.adapter = movieAdapter
        recycler_view_movies?.setHasFixedSize(true)

        //================================================================================================================================//

        text_input_search_movies?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {}

            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text.isNullOrEmpty()){
                    text_view_caption_title?.text = getString(R.string.genre_)
                    text_view_caption_description?.text = filterByGenre
                    filterMoviesByTitleOrGenre(genre = filterByGenre)
                } else {
                    filterMoviesByTitleOrGenre(title = text.toString())
                    text_view_caption_title?.text = getString(R.string.results_for)
                    text_view_caption_description?.text = text
                }
            }
        })

        //================================================================================================================================================================================================================================//

        moviesViewModel = ViewModelProvider(this, MoviesViewModel.MoviesViewModelFactory(application, MoviesRepository(this, MoviesDatabase.getInstanceDatabase(this).moviesDao()))).get(MoviesViewModel::class.java)
        moviesViewModel.moviesLiveData.observe(this, Observer { response ->
            if (response.isNotEmpty()) {
                movies = response
                changeLayoutVisibility(isLoading = false)
                filterMoviesByTitleOrGenre(genre = filterByGenre)
            } else {
                changeLayoutVisibility(isLoading = false, isEmpty = true)
            }
        })

        moviesViewModel.errorLivaData.observe(this, Observer {
            it?.let {
                changeLayoutVisibility(isLoading = false, isError = true)
            }
        })

        //Get all movies
        moviesViewModel.getMovies()
    }



    /**
     * Dialog sort movies by genre
     */
    private fun showDialogSortMoviesByGenre(){
        try {

            val viewDialog = LayoutInflater.from(this).inflate(R.layout.layout_dialog_rate_movies, null, false)
            val alertDialog = AlertDialog.Builder(this).setView(viewDialog).setCancelable(true).create()

            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.show()

            when(filterByGenre){
                getString(R.string.all) -> { viewDialog?.radio_button_all?.isChecked = true }
                getString(R.string.action) -> { viewDialog?.radio_button_action?.isChecked = true }
                getString(R.string.sci_fi) -> { viewDialog?.radio_button_sci_fi?.isChecked = true }
                getString(R.string.drama) -> { viewDialog?.radio_button_drama?.isChecked = true }
                getString(R.string.thriller) -> { viewDialog?.radio_button_thriller?.isChecked = true }
                getString(R.string.comedy) -> { viewDialog?.radio_button_comedy?.isChecked = true }
                getString(R.string.adventure) -> { viewDialog?.radio_button_adventure?.isChecked = true }
            }

            viewDialog?.radio_group_movies?.setOnCheckedChangeListener { _, radioButtonSelectedId ->
                when(radioButtonSelectedId){
                    R.id.radio_button_all -> { filterByGenre = getString(R.string.all) }
                    R.id.radio_button_action -> { filterByGenre = getString(R.string.action) }
                    R.id.radio_button_sci_fi -> { filterByGenre = getString(R.string.sci_fi) }
                    R.id.radio_button_drama -> { filterByGenre = getString(R.string.drama) }
                    R.id.radio_button_thriller -> { filterByGenre = getString(R.string.thriller) }
                    R.id.radio_button_comedy -> { filterByGenre = getString(R.string.comedy) }
                    R.id.radio_button_adventure -> { filterByGenre = getString(R.string.adventure) }
                }
            }

            viewDialog?.button_dialog_ok?.setOnClickListener {
                text_view_caption_title?.text = getString(R.string.genre_)
                text_view_caption_description?.text = filterByGenre
                filterMoviesByTitleOrGenre(genre = filterByGenre)
                Hawk.put(FILTER_BY_GENRE, filterByGenre)
                alertDialog.dismiss()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Filters the list of films by title or genre
     * @param title
     * @param genre
     */
    private fun filterMoviesByTitleOrGenre(title: String? = null, genre: String? = null){
        when {
            !title.isNullOrBlank() -> {movieAdapter.setMovies(movies.filter { movie ->
                movie.title?.toLowerCase(Locale.ROOT)!!.contains(title.toLowerCase(Locale.ROOT))
            })}
            !genre.isNullOrEmpty() && genre != getString(R.string.all) -> { movieAdapter.setMovies(movies.filter { movie ->
                movie.genre?.toLowerCase(Locale.ROOT)!!.contains(genre.toLowerCase(Locale.ROOT))
            })}
            else -> {
                movieAdapter.setMovies(movies)
            }
        }
    }

    /**
     * Change layout visibility
     * @param isLoading
     * @param isError
     * @param isEmpty
     */
    private fun changeLayoutVisibility(isLoading: Boolean, isError: Boolean = false, isEmpty: Boolean = false) {
        swipe_refresh_layout?.isRefreshing = false
        layout_loading?.visibility = if (isLoading) View.VISIBLE else View.GONE
        layout_no_movies?.visibility = if (isEmpty) View.VISIBLE else View.GONE
        layout_server_error?.visibility = if (isError) View.VISIBLE else View.GONE
        layout_container_movies?.visibility = if (!isLoading && !isError) View.VISIBLE else View.GONE
    }
}