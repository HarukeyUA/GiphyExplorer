package com.harukey.giphyexplorer.core.domain.model

import com.harukey.giphyexplorer.core.data.entity.GifImageEntity
import com.harukey.giphyexplorer.core.data.response.GifInfoResponse

data class GifImage(
    val id: String,
    val pagingOffset: Int,
    val previewUrl: String,
    val fullGifUrl: String
)

fun GifInfoResponse.toDomain(offset: Int): GifImage? {
    return GifImage(
        id = id ?: return null,
        previewUrl = images?.previewGif?.url ?: return null,
        fullGifUrl = images.original?.webp ?: return null,
        pagingOffset = offset
    )
}

fun GifImageEntity.toDomain(): GifImage {
    return GifImage(
        id = id,
        previewUrl = previewUrl,
        fullGifUrl = fullGifUrl,
        pagingOffset = pagingOffset
    )
}
