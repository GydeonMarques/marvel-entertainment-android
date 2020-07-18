package br.com.android.marvel.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.android.marvel.data.model.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_table")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMoviesFromDatabase(movies: List<Movie>)

    @Query("DELETE FROM movies_table")
    suspend fun deleteAll()
}