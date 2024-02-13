package com.woosuk.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.woosuk.data.persistence.entitiy.UserAccountEntity

@Database(entities = [UserAccountEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
