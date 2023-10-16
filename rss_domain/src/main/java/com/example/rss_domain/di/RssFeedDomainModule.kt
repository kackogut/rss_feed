package com.example.rss_domain.di

import com.example.rss_domain.DefaultSubscribeToRssFeedFromUrlUseCase
import com.example.rss_domain.SubscribeToRssFeedFromUrlUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RssFeedDomainModule {

    @Binds
    abstract fun bindSubscribeToRssFeedFromUrlUseCase(
        subscribeToRssFeedFromUrlUseCase: DefaultSubscribeToRssFeedFromUrlUseCase
    ): SubscribeToRssFeedFromUrlUseCase
}
