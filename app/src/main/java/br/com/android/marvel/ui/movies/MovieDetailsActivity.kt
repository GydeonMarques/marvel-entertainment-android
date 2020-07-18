package br.com.android.marvel.ui.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.android.marvel.data.model.Movie
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.ui.base.BaseActivity
import br.com.android.marvel.utils.LoadImageUtils
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*

class MovieDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as? Movie?

        movie?.let {
            setUpToolbar(toolbar, movie.title!!, true)

            text_view_genre?.text = movie.genre
            text_view_actors?.text = movie.actors
            text_view_duration?.text = movie.runtime
            text_view_description?.text = movie.plot
            text_view_director?.text = movie.director
            LoadImageUtils.loadImageAndApplyToImageView(this, image_view_poster!!, movie.poster, getDrawable(R.drawable.image_default))
        }
    }

    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        fun getIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movie)
            }
        }
    }
}