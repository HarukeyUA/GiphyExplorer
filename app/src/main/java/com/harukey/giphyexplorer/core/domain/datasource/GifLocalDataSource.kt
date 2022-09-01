package com.harukey.giphyexplorer.core.domain.datasource

import androidx.paging.PagingSource
import com.harukey.giphyexplorer.core.data.entity.GifImageEntity
import com.harukey.giphyexplorer.core.domain.model.GifImage

interface GifLocalDataSource {
    suspend fun insertInitialPage(page: List<GifImage>, term: String)
    suspend fun insertPage(page: List<GifImage>, term: String)
    fun getGifPagingSource(term: String): PagingSource<Int, GifImageEntity>
    suspend fun getLastItemOffset(term: String): Int
}
