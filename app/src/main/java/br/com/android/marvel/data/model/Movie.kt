package br.com.android.marvel.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies_table")
data class Movie(

    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("year")
    var year: String?,

    @SerializedName("rated")
    var rated: String?,

    @SerializedName("released")
    var released: String?,

    @SerializedName("runtime")
    var runtime: String?,

    @SerializedName("genre")
    var genre: String?,

    @SerializedName("director")
    var director: String?,

    @SerializedName("writer")
    var writer: String?,

    @SerializedName("actors")
    var actors: String?,

    @SerializedName("plot")
    var plot: String?,

    @SerializedName("poster")
    var poster: String?

) : Serializable