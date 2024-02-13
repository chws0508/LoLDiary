package com.woosuk.data.di

import com.woosuk.data.repository.DefaultMatchRepository
import com.woosuk.data.repository.DefaultUserRepository
import com.woosuk.domain.repository.MatchRepository
import com.woosuk.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsUserRepository(
        defaultUserRepository: DefaultUserRepository,
    ): UserRepository

    @Binds
    @Singleton
    fun bindsMatchRepository(
        defaultMatchRepository: DefaultMatchRepository,
    ): MatchRepository
}
