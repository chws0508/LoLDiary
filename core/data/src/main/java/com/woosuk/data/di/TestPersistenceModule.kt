package com.woosuk.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.woosuk.data.persistence.AppDatabase
import com.woosuk.data.persistence.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PersistenceModule::class],
)
object TestPersistenceModule {

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
    ): AppDatabase = Room.inMemoryDatabaseBuilder(
        appContext,
        AppDatabase::class.java,
    ).fallbackToDestructiveMigration() // 데이터베이스 마이그레이션 시, 기존 데이터를 다 날리고 마이그레이션 한다.
        .build()

    @Provides
    @Singleton
    fun provideUserDao(
        appDatabase: AppDatabase,
    ): UserDao = appDatabase.userDao()
}
