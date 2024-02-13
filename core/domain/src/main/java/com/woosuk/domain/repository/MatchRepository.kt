package com.woosuk.domain.repository

import com.woosuk.domain.model.UserMatchInfo
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    suspend fun getUserMatchInfoList(
        puuid: String,
        onError: (String) -> Unit,
    ): Flow<List<UserMatchInfo>>

    fun getUserMatchInfo(
        puuid: String,
        matchId: String,
        onError: (String) -> Unit,
    ): Flow<UserMatchInfo>
}
