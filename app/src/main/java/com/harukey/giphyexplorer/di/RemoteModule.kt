package com.harukey.giphyexplorer.di

import com.harukey.giphyexplorer.core.data.remote.GifRemoteDataSourceImpl
import com.harukey.giphyexplorer.core.domain.datasource.GifRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun provideGifSearchRemoteDataSource(dataSourceImpl: GifRemoteDataSourceImpl): GifRemoteDataSource
}
