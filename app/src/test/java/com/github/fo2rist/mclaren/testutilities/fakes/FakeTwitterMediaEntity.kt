package com.github.fo2rist.mclaren.testutilities.fakes

import com.github.fo2rist.mclaren.repository.converters.MEDIA_TYPE_PHOTO
import twitter4j.MediaEntity

const val SIZE_SMALL = 100
const val SIZE_MEDIUM = 500
const val SIZE_LARGE = 1000

const val MEDIA_URL_DEFAULT = "http://dummy.media.url"

class FakeTwitterMediaEntity(
    private val _type: String = MEDIA_TYPE_PHOTO,
    private val _url: String = MEDIA_URL_DEFAULT
) : MediaEntity {

    override fun getType(): String = _type

    override fun getURL(): String = _url

    override fun getMediaURL(): String = _url

    override fun getSizes(): MutableMap<Int, MediaEntity.Size> = mutableMapOf(
            MediaEntity.Size.LARGE to FakeSize(SIZE_LARGE, MediaEntity.Size.FIT),
            MediaEntity.Size.MEDIUM to FakeSize(SIZE_MEDIUM, MediaEntity.Size.FIT),
            MediaEntity.Size.SMALL to FakeSize(SIZE_SMALL, MediaEntity.Size.CROP)
    )

    override fun getVideoVariants(): Array<MediaEntity.Variant> = arrayOf(
            FakeVideoVariant(_url + "_cropped", "video/mp4"),
            FakeVideoVariant(_url, "video/mp4"),
            FakeVideoVariant(_url + "_thumb", "application/x-mpegURL")
    )

    override fun getVideoAspectRatioHeight(): Int = 1

    override fun getVideoAspectRatioWidth(): Int = 1

    override fun getEnd(): Int = throw NotImplementedError()

    override fun getId(): Long = throw NotImplementedError()

    override fun getText(): String = throw NotImplementedError()

    override fun getStart(): Int = throw NotImplementedError()

    override fun getMediaURLHttps(): String = throw NotImplementedError()

    override fun getVideoDurationMillis(): Long = throw NotImplementedError()

    override fun getDisplayURL(): String = throw NotImplementedError()

    override fun getExtAltText(): String = throw NotImplementedError()

    override fun getExpandedURL(): String = throw NotImplementedError()

    private class FakeSize(
        private val _size: Int,
        private val _resize: Int
    ) : MediaEntity.Size {
        override fun getHeight(): Int = _size

        override fun getWidth(): Int = _size

        override fun getResize(): Int = _resize
    }

    private class FakeVideoVariant(
        private val _url: String,
        private val _contentType: String
    ) : MediaEntity.Variant {
        override fun getUrl(): String = _url

        override fun getBitrate(): Int = 32000

        override fun getContentType(): String = _contentType

    }
}
