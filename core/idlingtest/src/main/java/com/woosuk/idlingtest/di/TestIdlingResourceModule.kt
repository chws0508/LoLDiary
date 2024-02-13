package com.woosuk.idlingtest.di

import com.woosuk.idlingtest.CountingIdlingResource
import com.woosuk.idlingtest.TestIdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [IdlingResourceModule::class]
)
object TestIdlingResourceModule {
    @Provides
    @Singleton
    fun provideIdlingResource(): CountingIdlingResource = TestIdlingResource()
}
