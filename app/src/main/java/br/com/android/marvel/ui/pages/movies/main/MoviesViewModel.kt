package br.com.android.marvel.ui.pages.movies.main

import androidx.lifecycle.*
import br.com.gms.domain.common.Error
import br.com.gms.domain.common.Result
import br.com.gms.domain.entity.MovieEntity
import br.com.gms.domain.usecase.MarvelUseCase
import br.com.gms.domain.usecase.MarvelUseCaseImpl
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val ALL = "Todos"

sealed class MoviesState {
    object Loading : MoviesState()
    data class Failed(var error: Error) : MoviesState()
    data class Success(var data: List<MovieEntity>) : MoviesState()
    data class OnSearch(var data: List<MovieEntity>) : MoviesState()
}

class MoviesViewModel @Inject constructor(
    private val useCase: MarvelUseCase
) : ViewModel() {

    private var _moviesList: List<MovieEntity> = arrayListOf()

    private val _state = MutableLiveData<MoviesState>()
    val state: LiveData<MoviesState> get() = _state

    fun getAllMovies(isConnected: Boolean) {
        if (state.value == null) _state.value = MoviesState.Loading
        viewModelScope.launch {
            when (isConnected) {
                true -> getRemoteMovies()
                else -> getLocalMovies()
            }
        }
    }

    private suspend fun getRemoteMovies() {
        when (val response = useCase.getRemoteMovies()) {
            is Result.Success -> {
                _moviesList = response.data
                useCase.salveAllMovies(response.data)
                _state.value = MoviesState.Success(response.data)
            }
            is Result.Error -> {
                _state.value = MoviesState.Failed(
                    Error(
                        code = response.code,
                        message = response.message,
                        exception = response.exception
                    )
                )
            }
        }
    }

    private suspend fun getLocalMovies() {
        useCase.getLocalMovies().run {
            observeForever(object : Observer<List<MovieEntity>> {
                override fun onChanged(t: List<MovieEntity>?) {
                    _state.value = MoviesState.Success(t ?: emptyList())
                    removeObserver(this)
                }
            })
        }
    }

    fun filterMoviesByTitleOrGenre(title: String? = null, genre: String? = null) {
        _state.value = MoviesState.OnSearch(
            when {
                !title.isNullOrBlank() && !genre.isNullOrBlank() && genre != ALL -> {
                    _moviesList.filter {
                        it.title.contains(title.lowercase(), ignoreCase = true)
                                && it.genre.contains(genre, ignoreCase = true)
                    }
                }
                !title.isNullOrBlank() -> {
                    _moviesList.filter { it.title.contains(title.lowercase(), ignoreCase = true) }
                }
                !genre.isNullOrEmpty() && genre != ALL -> {
                    _moviesList.filter { it.genre.contains(genre.lowercase(), ignoreCase = true) }
                }
                else -> {
                    _moviesList
                }
            }
        )
    }
}


