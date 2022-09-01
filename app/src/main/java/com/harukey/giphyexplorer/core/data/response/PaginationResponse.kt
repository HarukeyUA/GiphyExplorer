package com.harukey.giphyexplorer.core.data.response


import com.google.gson.annotations.SerializedName

data class PaginationResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("total_count")
    val totalCount: Int?
)
