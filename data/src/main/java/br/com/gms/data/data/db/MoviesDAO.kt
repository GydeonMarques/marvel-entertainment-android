package br.com.gms.data.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.gms.domain.entity.MovieEntity

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movies_table WHERE id = :id ")
    fun getMovieById(id: Int): LiveData<MovieEntity?>

    @Query("SELECT * FROM movies_table")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies_table")
    suspend fun deleteAll()
}