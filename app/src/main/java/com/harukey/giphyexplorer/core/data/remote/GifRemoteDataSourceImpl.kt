package com.harukey.giphyexplorer.core.data.remote

import com.harukey.giphyexplorer.core.data.response.GifSearchPageResponse
import com.harukey.giphyexplorer.core.domain.datasource.GifRemoteDataSource
import com.harukey.giphyexplorer.core.domain.model.GifImage
import com.harukey.giphyexplorer.core.domain.model.toDomain
import com.harukey.giphyexplorer.network.GiphyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifRemoteDataSourceImpl @Inject constructor(
    private val apiService: GiphyApi
) : GifRemoteDataSource {

    override suspend fun searchGifs(term: String, limit: Int, offset: Int): List<GifImage> {
        return apiService.searchGifs(term, limit, offset).let {
            it.content?.mapIndexedNotNull() { index, gif ->
                gif?.toDomain(
                    getGifPagingOffset(index, it)
                )
            }
                ?: listOf()
        }
    }

    private fun getGifPagingOffset(
        index: Int,
        gifResponse: GifSearchPageResponse
    ) = gifResponse.pagination?.offset?.plus(index) ?: 0
}
