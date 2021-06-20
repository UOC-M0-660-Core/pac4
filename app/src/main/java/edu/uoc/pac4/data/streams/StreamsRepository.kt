package edu.uoc.pac4.data.streams

import edu.uoc.pac4.data.network.OAuthException
import edu.uoc.pac4.data.streams.model.Stream
import kotlinx.coroutines.flow.Flow

/**
 * Created by alex on 12/09/2020.
 */

interface StreamsRepository {
    /// Returns a Flow containing a Pair object with
    /// first: Pagination cursor
    /// second: List of Streams
    @Throws(OAuthException::class)
    suspend fun fetchInitialStreams(forceRefresh: Boolean): Flow<Pair<String?, List<Stream>>>

    /// Returns a Pair object containing
    /// first: Pagination cursor
    /// second: List of Streams
    @Throws(OAuthException::class)
    suspend fun fetchMoreStreams(cursor: String?): Pair<String?, List<Stream>>
}