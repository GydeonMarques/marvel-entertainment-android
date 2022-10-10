package br.com.gms.data.data.model

import br.com.gms.domain.entity.MovieEntity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MovieResponse(

    @JsonProperty("id") var id: Int?,
    @JsonProperty("plot") var plot: String?,
    @JsonProperty("year") var year: String?,
    @JsonProperty("title") var title: String?,
    @JsonProperty("rated") var rated: String?,
    @JsonProperty("genre") var genre: String?,
    @JsonProperty("poster") var poster: String?,
    @JsonProperty("writer") var writer: String?,
    @JsonProperty("actors") var actors: String?,
    @JsonProperty("runtime") var runtime: String?,
    @JsonProperty("released") var released: String?,
    @JsonProperty("director") var director: String?,

    ) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id ?: 0,
            year = year ?: "",
            plot = plot ?: "",
            title = title ?: "",
            genre = genre ?: "",
            rated = rated ?: "",
            poster = poster ?: "",
            writer = writer ?: "",
            actors = actors ?: "",
            runtime = runtime ?: "",
            director = director ?: "",
            released = released ?: ""
        )
    }
}

