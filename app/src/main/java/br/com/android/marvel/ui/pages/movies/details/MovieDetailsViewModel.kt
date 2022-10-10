package br.com.android.marvel.ui.pages.movies.details

import androidx.lifecycle.*
import br.com.gms.data.data.db.MoviesDatabase
import br.com.gms.domain.entity.MovieEntity
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val database: MoviesDatabase
) : ViewModel() {

    fun getMovieById(id: Int): LiveData<MovieEntity?> {
        return database.moviesDAO().getMovieById(id)
    }
}