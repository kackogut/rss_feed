package com.example.rss_repository.di

import com.example.rss_repository.DefaultRssRepository
import com.example.rss_repository.RssRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RssReaderModule {

    @Binds
    abstract fun bindRssRepository(
        defaultRssRepository: DefaultRssRepository
    ): RssRepository
}
