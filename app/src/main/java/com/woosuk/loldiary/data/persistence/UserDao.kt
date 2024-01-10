package com.woosuk.loldiary.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.woosuk.loldiary.data.persistence.entitiy.UserAccountEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userAccountEntity: UserAccountEntity): Long

    @Query("SELECT * FROM UserAccountEntity WHERE isCurrentUser = 1 LIMIT 1")
    fun getCurrentUser(): UserAccountEntity?
}
