package br.com.gms.domain.usecase

import androidx.lifecycle.LiveData
import br.com.gms.domain.common.Result
import br.com.gms.domain.entity.MovieEntity
import br.com.gms.domain.repository.MarvelLocalRepository
import br.com.gms.domain.repository.MarvelRemoteRepository
import javax.inject.Inject

class MarvelUseCaseImpl @Inject constructor(
    private val localRepository: MarvelLocalRepository,
    private val remoteRepository: MarvelRemoteRepository
) : MarvelUseCase {

    override suspend fun getLocalMovies(): LiveData<List<MovieEntity>> {
        return localRepository.getAllMovies()
    }

    override suspend fun getRemoteMovies(): Result<List<MovieEntity>> {
        return remoteRepository.getAllMovies()
    }

    override suspend fun salveAllMovies(movies: List<MovieEntity>) {
        localRepository.saveAllMovies(movies)
    }
}