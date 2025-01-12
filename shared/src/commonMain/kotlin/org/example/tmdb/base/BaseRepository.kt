package org.example.tmdb.base

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import org.example.tmdb.utils.Async
import org.example.tmdb.utils.getNetworkFailed
import org.example.tmdb.utils.getRequestFailed
import org.example.tmdb.utils.getServerFailed
import org.example.tmdb.utils.getUnexpectedError

abstract class BaseRepository<T>(private val ioDispatcher: CoroutineDispatcher) {

    protected abstract suspend fun getSuccessResult(id: Any?): T

    fun getResult(id: Any? = null): Flow<Async<T>> = flow {
        emit(Async.Loading)
        try {
            emit(Async.Success(getSuccessResult(id)))
        } catch (e: IOException) {
            emit(Async.Error(getNetworkFailed()))
        } catch (e: ClientRequestException) {
            emit(Async.Error(getRequestFailed()))
        } catch (e: ServerResponseException) {
            emit(Async.Error(getServerFailed()))
        } catch (e: Exception) {
            emit(Async.Error(getUnexpectedError()))
        }
    }.flowOn(ioDispatcher)
}