package com.harukey.giphyexplorer.core.domain.interactor

import androidx.paging.*
import com.harukey.giphyexplorer.core.data.database.GifLocalDataSourceImpl
import com.harukey.giphyexplorer.core.data.entity.GifImageEntity
import com.harukey.giphyexplorer.core.domain.datasource.GifRemoteDataSource
import com.harukey.giphyexplorer.core.domain.model.GifImage
import com.harukey.giphyexplorer.core.domain.model.toDomain
import com.harukey.giphyexplorer.paging.GifSearchRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class GifInteractor @Inject constructor(
    private val gifLocalDataSourceImpl: GifLocalDataSourceImpl,
    private val gifRemoteDataSource: GifRemoteDataSource
) {

    fun getPaging(
        term: String
    ): Flow<PagingData<GifImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = GifSearchRemoteMediator(
                term,
                gifRemoteDataSource,
                gifLocalDataSourceImpl
            )
        ) {
            gifLocalDataSourceImpl.getGifPagingSource(term)
        }.flow.map { paging: PagingData<GifImageEntity> -> paging.map { it.toDomain() } }
    }
}
