package com.woosuk.data.network

import com.skydoves.sandwich.ApiResponse
import com.woosuk.data.network.dto.MatchDto
import com.woosuk.data.network.dto.UserUidDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AsiaService {
    @GET("riot/account/v1/accounts/by-riot-id/{gameName}/{tageLine}")
    suspend fun getAccount(
        @Path("gameName") gameName: String,
        @Path("tageLine") tagLine: String,
    ): ApiResponse<UserUidDto>

    @GET("lol/match/v5/matches/by-puuid/{puuid}/ids")
    suspend fun getUserMatchIds(
        @Path("puuid") puUid: String,
        @Query("start") start: Int,
        @Query("count") count: Int,
    ): ApiResponse<List<String>>

    @GET("lol/match/v5/matches/{matchId}")
    suspend fun getMatchInfo(
        @Path("matchId") matchId: String,
    ): ApiResponse<MatchDto>
}
