package com.harukey.giphyexplorer.core.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.harukey.giphyexplorer.core.data.entity.GifImageEntity

@Dao
interface GifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<GifImageEntity>)

    @Query("SELECT * FROM gif_images WHERE searchTerm = :term ORDER BY pagingOffset ASC")
    fun getGifPagingSource(term: String): PagingSource<Int, GifImageEntity>

    @Query("DELETE FROM gif_images WHERE searchTerm = :term")
    suspend fun deleteGifsByTerm(term: String)

    @Query("SELECT CASE COUNT(*) WHEN 0 THEN 0 ELSE MAX(pagingOffset) END FROM gif_images WHERE searchTerm = :term")
    suspend fun getLastItemOffset(term: String): Int

    @Transaction
    suspend fun insertRefreshPage(list: List<GifImageEntity>, term: String) {
        deleteGifsByTerm(term)
        insertAll(list)
    }
}
