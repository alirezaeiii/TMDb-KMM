package org.example.tmdb.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import org.example.tmdb.cache.Database
import org.example.tmdb.data.response.TMDbWrapper
import org.example.tmdb.data.response.asDomainModel
import org.example.tmdb.domain.repository.BaseFeedRepository
import org.example.tmdb.utils.getNowPlayingText
import org.example.tmdb.utils.getUpcomingText

class MovieFeedRepository(
    private val httpClient: HttpClient,
    database: Database,
    ioDispatcher: CoroutineDispatcher
) : BaseFeedRepository(database, ioDispatcher) {

    override suspend fun popularItems() =
        httpClient.get("movie/popular").body<TMDbWrapper>().items.asDomainModel()

    override suspend fun nowPlayingItems() =
        httpClient.get("movie/now_playing").body<TMDbWrapper>().items.asDomainModel()

    override suspend fun latestItems() =
        httpClient.get("movie/upcoming").body<TMDbWrapper>().items.asDomainModel()

    override suspend fun topRatedItems() =
        httpClient.get("movie/top_rated").body<TMDbWrapper>().items.asDomainModel()

    override suspend fun trendingItems() =
        httpClient.get("trending/movie/day").body<TMDbWrapper>().items.asDomainModel()

    override suspend fun discoverItems() =
        httpClient.get("discover/movie").body<TMDbWrapper>().items.asDomainModel()

    override fun getNowPlayingStringDesc() = getNowPlayingText()

    override fun getLatestStringDesc() = getUpcomingText()
}