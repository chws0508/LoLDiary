package com.woosuk.idlingtest.di

import com.woosuk.idlingtest.CountingIdlingResource
import com.woosuk.idlingtest.ProductIdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IdlingResourceModule {
    @Provides
    @Singleton
    fun provideIdlingResource(): CountingIdlingResource = ProductIdlingResource()
}
