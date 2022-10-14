package br.com.gms.data.repository

import androidx.lifecycle.LiveData
import br.com.gms.data.data.db.MoviesDAO
import br.com.gms.domain.entity.MovieEntity
import br.com.gms.domain.repository.MarvelLocalRepository
import javax.inject.Inject

class MarvelLocalRepositoryImpl @Inject constructor(
    private val moviesDAO: MoviesDAO,
) : MarvelLocalRepository {

    override suspend fun saveAllMovies(movies: List<MovieEntity>) {
        moviesDAO.deleteAll()
        moviesDAO.saveMovies(movies)
    }

    override suspend fun getAllMovies(): LiveData<List<MovieEntity>> {
        return moviesDAO.getAllMovies()
    }
}