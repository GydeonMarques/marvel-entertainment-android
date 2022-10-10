package br.com.android.marvel.ui.pages.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.android.marvel.entertainment.databinding.ItemMovieBinding
import br.com.gms.domain.entity.MovieEntity

class MoviesAdapter(
    private val onItemClickListener: OnItemMovieClickListener
) : ListAdapter<MovieEntity, MoviesAdapter.MovieViewHolder>(MovieDiffCallback()) {

    interface OnItemMovieClickListener {
        fun onClick(movie: MovieEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.getInstance(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holderMovie: MovieViewHolder, position: Int) {
        holderMovie.bindView(getItem(position))
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onItemClickListener: OnItemMovieClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getInstance(parent: ViewGroup, onItemClickListener: OnItemMovieClickListener): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(inflater, parent, false)
                return MovieViewHolder(binding, onItemClickListener)
            }
        }

        fun bindView(movie: MovieEntity) {
            binding.apply {
                onItemMovieClickListener = onItemClickListener
                this.movie = movie
                executePendingBindings()
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == oldItem
        }
    }
}