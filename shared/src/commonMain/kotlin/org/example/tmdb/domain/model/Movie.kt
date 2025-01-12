package org.example.tmdb.domain.model

data class Movie(
    val id: Int,
    val overview: String,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val name: String,
    val voteAverage: Double,
    val voteCount: Int
)