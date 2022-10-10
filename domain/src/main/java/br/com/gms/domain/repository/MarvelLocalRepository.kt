package br.com.gms.domain.repository

import androidx.lifecycle.LiveData
import br.com.gms.domain.entity.MovieEntity

interface MarvelLocalRepository {
    suspend fun saveAllMovies(movies: List<MovieEntity>)
    suspend fun getAllMovies(): LiveData<List<MovieEntity>>
}