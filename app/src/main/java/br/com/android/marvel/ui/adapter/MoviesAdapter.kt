package br.com.android.marvel.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.android.marvel.data.model.Movie
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.utils.LoadImageUtils
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val onItemClickListener: ((movie: Movie) -> Unit)) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var movies: List<Movie> = emptyList()

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(movies[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return if (!movies.isNullOrEmpty()) movies.size else 0
    }


    class ViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {

        private var title: TextView? = itemView.text_view_title
        private var genre: TextView? = itemView.text_view_genre
        private var poster: ImageView? = itemView.image_view_poster
        private var released: TextView? = itemView.text_view_release
        private var container: LinearLayout? = itemView.movie_container
        private var description: TextView? = itemView.text_view_description

        fun bindView(movie: Movie, onItemClickListener: ((movie: Movie) -> Unit)) {
            title?.text = movie.title
            genre?.text = movie.genre
            description?.text = movie.plot
            released?.text = movie.released
            container?.setOnClickListener { onItemClickListener.invoke(movie) }
            LoadImageUtils.loadImageAndApplyToImageView(context, poster!!, movie.poster, context.getDrawable(R.drawable.image_default))
        }
    }
}