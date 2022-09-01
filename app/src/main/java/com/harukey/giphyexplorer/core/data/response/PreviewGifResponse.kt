package com.harukey.giphyexplorer.core.data.response


import com.google.gson.annotations.SerializedName

data class PreviewGifResponse(
    @SerializedName("height")
    val height: String?,
    @SerializedName("size")
    val size: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: String?
)
