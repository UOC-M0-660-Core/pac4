package edu.uoc.pac4.data.streams

import kotlinx.coroutines.flow.Flow

/**
 * Created by alex on 11/21/20.
 */

// Feel free to change the methods and signatures as you prefer

class TwitchStreamsRepository(
    // TODO: Add any datasources you may need
) : StreamsRepository {

    override suspend fun fetchInitialStreams(forceRefresh: Boolean): Flow<Pair<String?, List<Stream>>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMoreStreams(cursor: String?): Pair<String?, List<Stream>> {
        TODO("Not yet implemented")
    }


}