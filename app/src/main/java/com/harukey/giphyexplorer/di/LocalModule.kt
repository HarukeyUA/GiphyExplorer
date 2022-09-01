package com.harukey.giphyexplorer.di

import com.harukey.giphyexplorer.core.data.database.GifLocalDataSourceImpl
import com.harukey.giphyexplorer.core.domain.datasource.GifLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalModule {

    @Binds
    @Singleton
    abstract fun provideGifLocalDataSource(gifLocalDataSourceImpl: GifLocalDataSourceImpl): GifLocalDataSource
}
