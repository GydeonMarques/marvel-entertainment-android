package br.com.android.marvel.data.api.controller

import br.com.android.marvel.data.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MoviesController {

    @GET("saga")
    fun getMoveis(): Call<List<Movie>>
}