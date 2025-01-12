package org.example.tmdb.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.tmdb.domain.model.Movie

@Serializable
data class TMDbWrapper(
    @SerialName("results")
    val items: List<MovieDTO>
)

@Serializable
data class MovieDTO(
    val id: Int,
    val overview: String,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("title")
    val name: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)

private fun MovieDTO.asDomainModel() = Movie(
    id = id,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath?.let { posterPath ->
        "http://image.tmdb.org/t/p/w342$posterPath"
    },
    backdropPath = backdropPath?.let { backdropPath ->
        "http://image.tmdb.org/t/p/w780$backdropPath"
    },
    name = name,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun List<MovieDTO>.asDomainModel() = map(MovieDTO::asDomainModel)