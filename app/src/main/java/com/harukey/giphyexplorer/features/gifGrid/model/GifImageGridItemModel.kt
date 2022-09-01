package com.harukey.giphyexplorer.features.gifGrid.model

import com.harukey.giphyexplorer.core.domain.model.GifImage

data class GifImageGridItemModel(
    val id: String,
    val pagingOffset: Int,
    val previewUrl: String,
    val fullGifUrl: String
)

fun GifImage.toItemModel() =
    GifImageGridItemModel(
        id = id,
        pagingOffset = pagingOffset,
        previewUrl = previewUrl,
        fullGifUrl = fullGifUrl
    )
