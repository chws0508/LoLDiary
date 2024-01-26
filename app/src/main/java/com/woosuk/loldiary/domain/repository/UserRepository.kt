package com.woosuk.loldiary.domain.repository

import com.woosuk.loldiary.domain.model.User
import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.model.UserRankInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(
        gameName: String,
        tageLine: String,
        onError: (String) -> Unit,
    ): Flow<User>

    suspend fun getUserAccount(
        gameName: String,
        tageLine: String,
        onError: (String) -> Unit,
    ): Flow<UserAccount>

    suspend fun getUserRankInfo(
        summonerId: String,
        onError: (String) -> Unit,
    ): Flow<UserRankInfo>

    suspend fun getCurrentUserAccount(): UserAccount?

    suspend fun saveCurrentUser(userAccount: UserAccount): Long

    suspend fun getPreviousUserAccounts(): List<UserAccount>

    suspend fun updateUserAccount(userAccount: UserAccount, isCurrentUser: Boolean)
}
