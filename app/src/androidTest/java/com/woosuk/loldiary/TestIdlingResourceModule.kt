package com.woosuk.loldiary

import com.woosuk.loldiary.di.IdlingResourceModule
import com.woosuk.loldiary.ui.utils.CountingIdlingResource
import com.woosuk.loldiary.ui.utils.TestIdlingResource
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
