package br.com.gms.data.data.api

import br.com.gms.data.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApiService {

    @GET("saga")
    suspend fun getMovies(): Response<List<MovieResponse>>
}