package com.harukey.giphyexplorer.core.domain.datasource

import com.harukey.giphyexplorer.core.domain.model.GifImage

interface GifRemoteDataSource {
    suspend fun searchGifs(term: String, limit: Int, offset: Int): List<GifImage>
}
