package com.woosuk.loldiary.di

import com.woosuk.loldiary.ui.utils.CountingIdlingResource
import com.woosuk.loldiary.ui.utils.ProductIdlingResource
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
