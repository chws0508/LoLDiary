package com.woosuk.loldiary.di

import com.woosuk.loldiary.data.repository.DefaultMatchRepository
import com.woosuk.loldiary.data.repository.DefaultUserRepository
import com.woosuk.loldiary.domain.repository.MatchRepository
import com.woosuk.loldiary.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
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
