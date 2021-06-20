package edu.uoc.pac4.data.streams.model

/**
 * Created by alex on 07/09/2020.
 */

data class Stream(
    val id: String,
    val userId: String? = null,
    val userName: String? = null,
    val gameId: String? = null,
    val title: String? = null,
    val viewerCount: Int,
    val startedAtString: String? = null,
    val language: String? = null,
    val thumbnailUrl: String? = null,
) {

    fun getSizedImage(imageUrl: String, width: Int, height: Int): String {
        return imageUrl
            .replace("{width}", width.toString())
            .replace("{height}", height.toString())
    }
}