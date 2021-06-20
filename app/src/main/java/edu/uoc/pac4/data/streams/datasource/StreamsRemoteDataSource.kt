package edu.uoc.pac4.data.streams.datasource

import edu.uoc.pac4.data.network.Endpoints
import edu.uoc.pac4.data.network.OAuthException
import edu.uoc.pac4.data.streams.datasource.model.StreamsApiResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by alex on 12/09/2020.
 */

class StreamsRemoteDataSource(private val client: HttpClient) {

    suspend fun getStreams(cursor: String?): StreamsApiResponse = withContext(Dispatchers.IO) {
        try {
            val response = client.get<StreamsApiResponse>(Endpoints.streamsUrl) {
                cursor?.let { parameter("after", it) }
            }
            response
        } catch (t: Throwable) {
            (t as? ClientRequestException)?.let {
                if (it.response?.status == HttpStatusCode.Unauthorized) {
                    throw OAuthException.UnauthorizedException
                }
            }
            throw t
        }
    }
}