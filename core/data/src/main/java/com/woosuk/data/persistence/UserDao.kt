package com.woosuk.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.woosuk.data.persistence.entitiy.UserAccountEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userAccountEntity: UserAccountEntity): Long

    @Query("SELECT * FROM UserAccountEntity WHERE isCurrentUser = 1 LIMIT 1")
    fun getCurrentUser(): UserAccountEntity?

    @Query("SELECT * FROM UserAccountEntity WHERE isCurrentUser = 0")
    fun getPreviousUsers(): List<UserAccountEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(vararg userAccountEntity: UserAccountEntity)
}
