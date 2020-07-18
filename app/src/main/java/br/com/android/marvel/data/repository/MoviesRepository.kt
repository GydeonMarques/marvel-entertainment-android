package br.com.android.marvel.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import br.com.android.marvel.data.api.controller.MoviesController
import br.com.android.marvel.data.api.service.Api
import br.com.android.marvel.data.dao.MoviesDao
import br.com.android.marvel.data.model.Movie
import br.com.android.marvel.data.response.ErrorDataResponse
import br.com.android.marvel.entertainment.R
import io.andref.rx.network.RxNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository(private var context: Context, private var moviesDao: MoviesDao) {

    var isConnected: Boolean = true
    var moviesLiveData = MutableLiveData<List<Movie>>()
    var errorLivaData = MutableLiveData<ErrorDataResponse>()

    init {
        RxNetwork.connectivityChanges(context, context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).subscribe {connected ->
            if (!connected) Toast.makeText(context, context.getString(R.string.no_internet_connection_offline_browsing_mode), Toast.LENGTH_LONG).show()
            this.isConnected  = connected
        }
    }
    /**
     * Public method that checks if the devices are connected to the internet, if connected to the internet, and the list of movies from the api is returned.
     * If the device is not connected to the internet, the list of movies saved in the database is returned.
     */
    fun getAllMovies() {
        if (this.isConnected) {
            getMoviesFromApi()
        } else {
            getAllMoviesFromDatabase()
            Toast.makeText(context, context.getString(R.string.no_internet_connection_offline_browsing_mode), Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Get all the movies from the api
     */
    private fun getMoviesFromApi() {
        Api.createService(MoviesController::class.java)
            .getMoveis()
            .enqueue(object : Callback<List<Movie>> {
                override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                saveMoviesFromDatabase(it)
                            }
                            moviesLiveData.value = response.body()
                            errorLivaData.value = null
                        }
                    } else {
                        errorLivaData.value = ErrorDataResponse(context.getString(R.string.could_not_get_the_list_of_movies))
                    }
                }

                override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                    errorLivaData.value = ErrorDataResponse(context.getString(R.string.could_not_get_the_list_of_movies))
                }
            })
    }

    /**
     * Get all movies from the database
     */
    private fun getAllMoviesFromDatabase() {
        moviesDao.getAllMovies().observeForever { moviesLiveData.value = it }
    }

    /**
     * Save movies from database
     * @param movies
     */
    suspend fun saveMoviesFromDatabase(movies: List<Movie>) {
        moviesDao.deleteAll()
        moviesDao.saveMoviesFromDatabase(movies)
    }
}