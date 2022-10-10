package br.com.gms.data.repository

import androidx.lifecycle.LiveData
import br.com.gms.data.data.db.MoviesDatabase
import br.com.gms.domain.entity.MovieEntity
import br.com.gms.domain.repository.MarvelLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelLocalRepositoryImpl @Inject constructor(
    private val databaseI: MoviesDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MarvelLocalRepository {

    override suspend fun saveAllMovies(movies: List<MovieEntity>) {
        withContext(dispatcher) {
            databaseI.moviesDAO().deleteAll()
            databaseI.moviesDAO().saveMovies(movies)
        }
    }

    override suspend fun getAllMovies(): LiveData<List<MovieEntity>> {
        return databaseI.moviesDAO().getAllMovies()
    }
}