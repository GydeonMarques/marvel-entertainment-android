package br.com.gms.data.repository

import br.com.gms.data.data.api.MarvelApiService
import br.com.gms.domain.common.Result
import br.com.gms.domain.entity.MovieEntity
import br.com.gms.domain.repository.MarvelRemoteRepository
import com.fasterxml.jackson.core.JsonParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelRemoteRepositoryImpl @Inject constructor(
    private val marvelApi: MarvelApiService,
    private val dispatcher: CoroutineDispatcher
) : MarvelRemoteRepository {

    override suspend fun getAllMovies(): Result<List<MovieEntity>> {
        return try {
            withContext(dispatcher) {
                with(marvelApi.getMovies()) {

                    body()?.let {
                        var index = 1

                        val listOfMovies = it.map { movieEntity ->
                            //Geramos um id padrão para cada filme, pois os mesmos não possui id quando são retornados da api.
                            movieEntity.copy(id = index++).toMovieEntity()
                        }

                        return@withContext Result.Success(listOfMovies)
                    }

                    errorBody()?.let {
                        return@withContext Result.Error(message = it.string())
                    }

                    return@withContext Result.Error().default()
                }
            }
        } catch (e: Exception) {
            return Result.Error(e.localizedMessage)
        }
    }
}