package br.com.gms.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies_table")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var year: String,
    var plot: String,
    var title: String,
    var rated: String,
    var genre: String,
    var writer: String,
    var poster: String,
    var actors: String,
    var runtime: String,
    var director: String,
    var released: String

) : Serializable