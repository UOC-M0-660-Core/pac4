package edu.uoc.pac4.data.streams.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.uoc.pac4.data.streams.model.Stream

/**
 * Created by alex on 20/6/21.
 */
@Entity
data class StreamDbModel(
    @PrimaryKey val id: String,
    val userId: String? = null,
    val userName: String? = null,
    val gameId: String? = null,
    val title: String? = null,
    val viewerCount: Int? = null,
    val startedAtString: String? = null,
    val language: String? = null,
    val thumbnailUrl: String? = null,
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

    companion object {
        fun fromStream(stream: Stream): StreamDbModel {
            return StreamDbModel(
                stream.id,
                stream.userId,
                stream.userName,
                stream.gameId,
                stream.title,
                stream.viewerCount,
                stream.startedAtString,
                stream.language,
                stream.thumbnailUrl,
            )
        }
    }
}
