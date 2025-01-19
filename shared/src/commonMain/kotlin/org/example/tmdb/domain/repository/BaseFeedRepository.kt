package org.example.tmdb.domain.repository

import dev.icerock.moko.resources.desc.StringDesc
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import org.example.tmdb.cache.Database
import org.example.tmdb.domain.model.FeedWrapper
import org.example.tmdb.domain.model.Movie
import org.example.tmdb.domain.model.SortType
import org.example.tmdb.utils.Async
import org.example.tmdb.utils.getDiscoverText
import org.example.tmdb.utils.getHighestRateText
import org.example.tmdb.utils.getNetworkFailed
import org.example.tmdb.utils.getPopularText
import org.example.tmdb.utils.getRequestFailed
import org.example.tmdb.utils.getServerFailed
import org.example.tmdb.utils.getTrendingText
import org.example.tmdb.utils.getUnexpectedError

abstract class BaseFeedRepository(
    private val database: Database,
    private val ioDispatcher: CoroutineDispatcher
) {

    protected abstract suspend fun popularItems(): List<Movie>

    protected abstract suspend fun nowPlayingItems(): List<Movie>

    protected abstract suspend fun latestItems(): List<Movie>

    protected abstract suspend fun topRatedItems(): List<Movie>

    protected abstract suspend fun trendingItems(): List<Movie>

    protected abstract suspend fun discoverItems(): List<Movie>

    protected abstract fun getNowPlayingStringDesc(): StringDesc

    protected abstract fun getLatestStringDesc(): StringDesc

    fun getResult(): Flow<Async<List<FeedWrapper>>> = flow {
        emit(Async.Loading)
        val dbMovies = database.getAllMovies()
        if (dbMovies.isEmpty()) {
            try {
                emitUpdatedMovies()
            } catch (e: IOException) {
                emit(Async.Error(getNetworkFailed()))
            } catch (e: ClientRequestException) {
                emit(Async.Error(getRequestFailed()))
            } catch (e: ServerResponseException) {
                emit(Async.Error(getServerFailed()))
            } catch (e: Exception) {
                emit(Async.Error(getUnexpectedError()))
            }
        } else {
            emit(Async.Success(getFeedWrappers(dbMovies)))
            try {
                emitUpdatedMovies()
            } catch (t: Throwable) {
                Napier.e("Network failed", t)
            }
        }
    }.flowOn(ioDispatcher)

    private suspend fun FlowCollector<Async.Success<List<FeedWrapper>>>.emitUpdatedMovies() {
        val movieWrapper = getMoviesFromNetwork()
        database.clearAndCreateMovies(movieWrapper)
        val dbMovies = database.getAllMovies()
        emit(Async.Success(getFeedWrappers(dbMovies)))
    }

    private suspend fun getMoviesFromNetwork(): List<List<Movie>> {
        val trendingDeferred: Deferred<List<Movie>>
        val nowPlayingDeferred: Deferred<List<Movie>>
        val popularDeferred: Deferred<List<Movie>>
        val latestDeferred: Deferred<List<Movie>>
        val topRatedDeferred: Deferred<List<Movie>>
        val discoverDeferred: Deferred<List<Movie>>
        coroutineScope {
            trendingDeferred = async { trendingItems() }
            nowPlayingDeferred = async { nowPlayingItems() }
            popularDeferred = async { popularItems() }
            latestDeferred = async { latestItems() }
            topRatedDeferred = async { topRatedItems() }
            discoverDeferred = async { discoverItems() }
        }
        return listOf(
            trendingDeferred.await(),
            popularDeferred.await(),
            nowPlayingDeferred.await(),
            discoverDeferred.await(),
            latestDeferred.await(),
            topRatedDeferred.await()
        )
    }

    private fun getFeedWrappers(movieWrapper: List<List<Movie>>) = listOf(
        FeedWrapper(
            movieWrapper[0],
            getTrendingText(),
            SortType.TRENDING,
        ),
        FeedWrapper(
            movieWrapper[1],
            getPopularText(),
            SortType.MOST_POPULAR,
        ),
        FeedWrapper(
            movieWrapper[2],
            getNowPlayingStringDesc(),
            SortType.NOW_PLAYING,
        ),
        FeedWrapper(
            movieWrapper[3],
            getDiscoverText(),
            SortType.DISCOVER,
        ),
        FeedWrapper(
            movieWrapper[4],
            getLatestStringDesc(),
            SortType.UPCOMING,
        ),
        FeedWrapper(
            movieWrapper[5],
            getHighestRateText(),
            SortType.HIGHEST_RATED,
        )
    )
}