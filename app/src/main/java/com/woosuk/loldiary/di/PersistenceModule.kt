package com.woosuk.loldiary.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.woosuk.loldiary.data.persistence.AppDatabase
import com.woosuk.loldiary.data.persistence.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "LoL Diary")
    private const val DATABASE_NAME = "LOL_DIARY_DATABASE"

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext appContext: Context,
    ): DataStore<Preferences> {
        return appContext.dataStore
    }

    @Provides
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        DATABASE_NAME,
    ).fallbackToDestructiveMigration() // 데이터베이스 마이그레이션 시, 기존 데이터를 다 날리고 마이그레이션 한다.
        .build()

    @Provides
    @Singleton
    fun provideUserDao(
        appDatabase: AppDatabase,
    ): UserDao = appDatabase.userDao()
}
