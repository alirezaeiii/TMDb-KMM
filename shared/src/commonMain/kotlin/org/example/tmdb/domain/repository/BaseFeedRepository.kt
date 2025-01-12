package org.example.tmdb.domain.repository

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.example.tmdb.base.BaseRepository
import org.example.tmdb.domain.model.FeedWrapper
import org.example.tmdb.domain.model.Movie
import org.example.tmdb.domain.model.SortType
import org.example.tmdb.utils.getDiscoverText
import org.example.tmdb.utils.getHighestRateText
import org.example.tmdb.utils.getPopularText
import org.example.tmdb.utils.getTrendingText

abstract class BaseFeedRepository(ioDispatcher: CoroutineDispatcher) :
    BaseRepository<List<FeedWrapper>>(ioDispatcher) {

    protected abstract suspend fun popularItems(): List<Movie>

    protected abstract suspend fun nowPlayingItems(): List<Movie>

    protected abstract suspend fun latestItems(): List<Movie>

    protected abstract suspend fun topRatedItems(): List<Movie>

    protected abstract suspend fun trendingItems(): List<Movie>

    protected abstract suspend fun discoverItems(): List<Movie>

    protected abstract fun getNowPlayingStringDesc(): StringDesc

    protected abstract fun getLatestStringDesc(): StringDesc

    override suspend fun getSuccessResult(id: Any?): List<FeedWrapper> {
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
            FeedWrapper(
                trendingDeferred.await(),
                getTrendingText(),
                SortType.TRENDING,
            ),
            FeedWrapper(
                popularDeferred.await(),
                getPopularText(),
                SortType.MOST_POPULAR,
            ),
            FeedWrapper(
                nowPlayingDeferred.await(),
                getNowPlayingStringDesc(),
                SortType.NOW_PLAYING,
            ),
            FeedWrapper(
                discoverDeferred.await(),
                getDiscoverText(),
                SortType.DISCOVER,
            ),
            FeedWrapper(
                latestDeferred.await(),
                getLatestStringDesc(),
                SortType.UPCOMING,
            ),
            FeedWrapper(
                topRatedDeferred.await(),
                getHighestRateText(),
                SortType.HIGHEST_RATED,
            ),
        )
    }
}