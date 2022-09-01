package com.harukey.giphyexplorer.core.data.database

import androidx.paging.PagingSource
import com.harukey.giphyexplorer.core.data.entity.GifImageEntity
import com.harukey.giphyexplorer.core.data.entity.IgnoredGifEntity
import com.harukey.giphyexplorer.core.data.entity.toEntity
import com.harukey.giphyexplorer.core.domain.datasource.GifLocalDataSource
import com.harukey.giphyexplorer.core.domain.model.GifImage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifLocalDataSourceImpl @Inject constructor(
    private val gifDao: GifDao
): GifLocalDataSource {

    override suspend fun insertInitialPage(page: List<GifImage>, term: String) {
        gifDao.insertRefreshPage(page.map { it.toEntity(term) }, term)
    }

    override suspend fun insertPage(page: List<GifImage>, term: String) {
        gifDao.insertAll(page.map { it.toEntity(term) })
    }

    override fun getGifPagingSource(term: String): PagingSource<Int, GifImageEntity> {
        return gifDao.getGifPagingSource(term)
    }

    override suspend fun getLastItemOffset(term: String): Int {
        return gifDao.getLastItemOffset(term)
    }

    override suspend fun insertIgnoredGifId(id: String) {
        gifDao.insertIgnoredId(IgnoredGifEntity(id))
    }
}
