package br.com.gms.domain.repository

import br.com.gms.domain.common.Result
import br.com.gms.domain.entity.MovieEntity

interface MarvelRemoteRepository {
    suspend fun getAllMovies(): Result<List<MovieEntity>>
}