package edu.uoc.pac4.data.streams

import edu.uoc.pac4.data.streams.datasource.StreamDao
import edu.uoc.pac4.data.streams.datasource.StreamsRemoteDataSource
import edu.uoc.pac4.data.streams.datasource.model.StreamDbModel
import edu.uoc.pac4.data.streams.model.Stream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Created by alex on 11/21/20.
 */

class TwitchStreamsRepository(
    private val remoteDataSource: StreamsRemoteDataSource,
    private val localDataSource: StreamDao,
) : StreamsRepository {

    override suspend fun fetchInitialStreams(forceRefresh: Boolean): Flow<Pair<String?, List<Stream>>> =
        flow {
            if (!forceRefresh) {
                // Emit local streams
                val localStreams = localDataSource.getAll()
                emit(Pair(null, localStreams.map { it.toStream() }))
            } else {
                // Clear local streams
                localDataSource.deleteAll()
            }
            // Fech new streams
            val apiStreamsResponse = remoteDataSource.getStreams(null)
            // Got Streams
            val apiStreams = apiStreamsResponse.data.orEmpty()
            // Save locally
            localDataSource.insertAll(apiStreams.map { it.toStream() }
                .map { StreamDbModel.fromStream(it) })
            // Convert to Pair<Key, Value> to keep the twitch response and pagination
            // in the data layer
            emit(Pair(apiStreamsResponse.pagination?.cursor, apiStreams.map { it.toStream() }))
        }.flowOn(Dispatchers.IO)


    override suspend fun fetchMoreStreams(cursor: String?): Pair<String?, List<Stream>> {
        val response = remoteDataSource.getStreams(cursor)
        val apiStreams = response.data.orEmpty()
        // Save to db
        withContext(Dispatchers.IO) {
            localDataSource.insertAll(apiStreams.map { it.toStream() }
                .map { StreamDbModel.fromStream(it) })
        }
        // Return
        return Pair(response.pagination?.cursor, apiStreams.map { it.toStream() })
    }

}