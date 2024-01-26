package com.woosuk.loldiary.data.repository

import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.woosuk.loldiary.data.network.AsiaService
import com.woosuk.loldiary.data.network.KrService
import com.woosuk.loldiary.data.network.dto.UserProfileDto
import com.woosuk.loldiary.data.network.dto.toDomain
import com.woosuk.loldiary.data.persistence.UserDao
import com.woosuk.loldiary.data.persistence.entitiy.toDomain
import com.woosuk.loldiary.data.persistence.entitiy.toEntity
import com.woosuk.loldiary.domain.model.User
import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.model.UserRankInfo
import com.woosuk.loldiary.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val asiaService: AsiaService,
    private val krService: KrService,
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun getUser(
        gameName: String,
        tageLine: String,
        onError: (String) -> Unit,
    ): Flow<User> = flow<User> {
        val userAccount =
            getUserAccount(gameName, tageLine) { throw IllegalStateException(it) }.single()

        val userProfile = getUserProfileDto(userAccount.uid) { throw IllegalStateException(it) }
            .single()
            .toDomain()

        val userRankInfo =
            getUserRankInfo(userAccount.summonerId) { throw IllegalStateException(it) }
                .single()

        emit(
            User(
                userAccount = userAccount,
                userProfile = userProfile,
                userRankInfo = userRankInfo
            )
        )
    }.catch { onError(it.message.toString()) }.flowOn(Dispatchers.IO)

    override suspend fun getUserAccount(
        gameName: String,
        tageLine: String,
        onError: (String) -> Unit,
    ): Flow<UserAccount> {
        val uid = getUserUid(gameName, tageLine, onError = { onError(it) }).first()
        return getUserProfileDto(uid) { onError(it) }.map {
            UserAccount(
                uid,
                summonerId = it.id,
                gameName = gameName,
                tageLine = tageLine,
                isCurrentUser = false,
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserRankInfo(
        summonerId: String,
        onError: (String) -> Unit,
    ): Flow<UserRankInfo> = flow {
        krService.getUserLeagueInfo(summonerId).suspendOnSuccess {
            emit(data.first { it.queueType == "RANKED_SOLO_5x5" }.toDomain())
        }.suspendOnFailure {
            onError(message())
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getUserUid(
        gameName: String,
        tageLine: String,
        onError: (String) -> Unit,
    ): Flow<String> = flow {
        asiaService.getAccount(gameName, tageLine).suspendOnSuccess {
            emit(data.puuid)
        }.suspendOnFailure {
            onError(message())
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getUserProfileDto(
        uid: String,
        onError: (String) -> Unit,
    ): Flow<UserProfileDto> = flow {
        krService.getUserProfile(uid).suspendOnSuccess {
            emit(data)
        }.suspendOnFailure {
            onError(message())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCurrentUserAccount(): UserAccount? = withContext(Dispatchers.IO) {
        userDao.getCurrentUser()?.toDomain()
    }

    override suspend fun saveCurrentUser(
        userAccount: UserAccount,
    ) = userDao.saveUser(userAccount.toEntity(true))

    override suspend fun getPreviousUserAccounts(): List<UserAccount> {
        return userDao.getPreviousUsers().toDomain()
    }

    override suspend fun updateUserAccount(userAccount: UserAccount, isCurrentUser: Boolean) {
        val userAccountEntity = userAccount.toEntity(isCurrentUser)
        userDao.updateUsers(userAccountEntity)
    }
}
