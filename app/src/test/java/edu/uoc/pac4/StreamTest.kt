package edu.uoc.pac4

import edu.uoc.pac4.data.streams.model.Stream
import org.junit.Test

/**
 * Local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StreamTest {
    @Test
    fun streamImage_isWidthAndHeightReplaced() {
        val widthPlaceholder = "{width}"
        val heightPlaceholder = "{height}"

        val imageWidth = 1000
        val imageHeight = 250

        val stream = Stream(
            id = "1234",
            thumbnailUrl = "https://static-cdn.jtvnw.net/previews-ttv/live_user_trainwreckstv-${widthPlaceholder}x${heightPlaceholder}.jpg"
        )
        val sizedImageUrl = stream.getSizedImage(
            imageUrl = stream.thumbnailUrl!!,
            width = imageWidth,
            height = imageHeight
        )

        assert(
            !sizedImageUrl.contains(widthPlaceholder) && !sizedImageUrl.contains(
                heightPlaceholder
            )
        ) {
            "Sized image URL cannot contain $widthPlaceholder and $heightPlaceholder placeholders"
        }
        assert(sizedImageUrl.contains("${imageWidth}x${imageHeight}")) {
            "Sized image URL must contain given width x height values"
        }
    }
}