package com.example.rss_reader.di

import com.example.rss_reader.DefaultRssParser
import com.example.rss_reader.DefaultRssReader
import com.example.rss_reader.RssParser
import com.example.rss_reader.RssReader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RssReaderModule {

    @Binds
    abstract fun bindRssParser(
        defaultRssReader: DefaultRssParser
    ): RssParser

    @Binds
    abstract fun bindRssReader(
        defaultRssReader: DefaultRssReader
    ): RssReader
}
