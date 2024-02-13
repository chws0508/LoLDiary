package com.woosuk.data.repository

import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.woosuk.data.network.AsiaService
import com.woosuk.data.network.dto.toUserMatchInfo
import com.woosuk.domain.model.UserMatchInfo
import com.woosuk.domain.repository.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import javax.inject.Inject

internal class DefaultMatchRepository @Inject constructor(
    private val asiaService: AsiaService,
) : MatchRepository {

    override suspend fun getUserMatchInfoList(
        puuid: String,
        onError: (String) -> Unit,
    ) = flow {
        val data = coroutineScope {
            val matchIds = getUserMatchIds(puuid) { throw IllegalStateException(it) }.single()
            val deferredMatchInfoList = matchIds.map {
                async { getUserMatchInfo(puuid = puuid, matchId = it) { throw IllegalStateException(it) }.single() }
            }
            deferredMatchInfoList.awaitAll()
        }
        emit(data)
    }.catch { onError(it.toString()) }.flowOn(Dispatchers.IO)

    private fun getUserMatchIds(
        puuid: String,
        start: Int = 0,
        count: Int = 15,
        onError: (String) -> Unit,
    ): Flow<List<String>> = flow {
        asiaService.getUserMatchIds(
            puUid = puuid,
            start = start,
            count = count,
        ).suspendOnSuccess {
            emit(data)
        }.suspendOnFailure {
            onError(this.message())
        }
    }

    override fun getUserMatchInfo(
        puuid: String,
        matchId: String,
        onError: (String) -> Unit,
    ): Flow<UserMatchInfo> = flow {
        asiaService.getMatchInfo(matchId).suspendOnSuccess {
            emit(data.toUserMatchInfo(puuid))
        }.suspendOnFailure {
            onError(message())
        }
    }
}
