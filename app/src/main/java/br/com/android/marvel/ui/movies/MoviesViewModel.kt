package br.com.android.marvel.ui.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.android.marvel.data.model.Movie
import br.com.android.marvel.data.repository.MoviesRepository
import br.com.android.marvel.data.response.ErrorDataResponse

@Suppress("UNCHECKED_CAST")
class MoviesViewModel(application: Application, private val moviesRepository: MoviesRepository) : AndroidViewModel(application) {

    var moviesLiveData = MutableLiveData<List<Movie>>()
    var errorLivaData = MutableLiveData<ErrorDataResponse>()


    /**
     * Returns the list of available movies
     */
    fun getMovies() {
        moviesRepository.moviesLiveData.observeForever { moviesLiveData.value = it }
        moviesRepository.errorLivaData.observeForever { errorLivaData.value = it }
        moviesRepository.getAllMovies()
    }

    class MoviesViewModelFactory(private val application: Application, private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MoviesViewModel(application, moviesRepository) as T
        }
    }
}