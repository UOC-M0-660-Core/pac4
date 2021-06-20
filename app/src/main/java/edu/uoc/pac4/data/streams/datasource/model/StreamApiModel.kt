package edu.uoc.pac4.data.streams.datasource.model

import edu.uoc.pac4.data.streams.model.Stream
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by alex on 07/09/2020.
 */

@Serializable
data class StreamApiModel(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("user_name") val userName: String? = null,
    @SerialName("game_id") val gameId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("viewer_count") val viewerCount: Int? = null,
    @SerialName("started_at") val startedAtString: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
) {

    fun toStream(): Stream {
        return Stream(
            id,
            userId,
            userName,
            gameId,
            title,
            viewerCount ?: 0,
            startedAtString,
            language,
            thumbnailUrl,
        )
    }
}

@Serializable
data class StreamsApiResponse(
    @SerialName("data") val data: List<StreamApiModel>? = null,
    @SerialName("pagination") val pagination: Pagination? = null,
)

@Serializable
data class Pagination(
    @SerialName("cursor") val cursor: String? = null,
)