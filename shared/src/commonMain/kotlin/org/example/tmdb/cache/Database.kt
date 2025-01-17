package org.example.tmdb.cache

import app.cash.sqldelight.Query
import org.example.tmdb.domain.model.Movie
import org.example.tmdb.cache.MovieTable as MovieEntity

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllMovies(): List<List<Movie>> {
        val movieWrapper = ArrayList<List<Movie>>()
        for(parentId in getAllParentIds()) {
            val movies = getMovies(parentId)
            movieWrapper.add(movies)
        }
        return movieWrapper
    }

    internal fun clearAndCreateMovies(movieWrapper: List<List<Movie>>) {
        dbQuery.transaction {
            dbQuery.removeAll()
            dbQuery.removeAllMovies()
            movieWrapper.forEachIndexed { parentIndex, movies ->
                dbQuery.insertParent(parentIndex.toLong())
                movies.forEachIndexed { index, movie ->
                    dbQuery.insert(movie.id.toLong(),
                        movie.overview,
                        movie.releaseDate,
                        movie.posterPath,
                        movie.backdropPath,
                        movie.name,
                        movie.voteAverage,
                        movie.voteCount.toLong(),
                        index.toLong(),
                        getParentId(parentIndex))
                }
            }
        }
    }

    private fun getParentId(index: Int) = dbQuery.selectParentId(index.toLong()).executeAsOne()

    private fun getAllParentIds(): List<Long> = dbQuery.selectParentsOrdered().executeAsList()

    private fun getMovies(parentId: Long): List<Movie> {
        return dbQuery.selectMoviesOrdered(parentId).asDomainModel()
    }

    private fun Query<MovieEntity>.asDomainModel(): List<Movie> {
        return executeAsList().map { dbMovie ->
            Movie(
                id = dbMovie.id.toInt(),
                overview = dbMovie.overview,
                releaseDate = dbMovie.releaseDate,
                posterPath = dbMovie.posterPath,
                backdropPath = dbMovie.backdropPath,
                name = dbMovie.name,
                voteAverage = dbMovie.voteAverage,
                voteCount = dbMovie.voteCount.toInt()
            )
        }
    }
}