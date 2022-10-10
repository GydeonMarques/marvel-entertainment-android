package br.com.gms.domain.usecase

import androidx.lifecycle.LiveData
import br.com.gms.domain.common.Result
import br.com.gms.domain.entity.MovieEntity
import br.com.gms.domain.repository.MarvelLocalRepository
import br.com.gms.domain.repository.MarvelRemoteRepository
import javax.inject.Inject

interface MarvelUseCase {
    suspend fun getLocalMovies(): LiveData<List<MovieEntity>>
    suspend fun getRemoteMovies(): Result<List<MovieEntity>>
    suspend fun salveAllMovies(movies: List<MovieEntity>)
}