package com.harukey.giphyexplorer.network

import com.harukey.giphyexplorer.core.data.response.GifSearchPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/search")
    suspend fun searchGifs(
        @Query("q") searchTerm: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = DEFAULT_RATING,
        @Query("lang") lang: String = DEFAULT_LANGUAGE
    ): GifSearchPageResponse

    companion object {
        const val DEFAULT_LANGUAGE = "en"
        const val DEFAULT_RATING = "g"
    }

}
