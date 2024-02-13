package com.woosuk.data.network

import com.skydoves.sandwich.ApiResponse
import com.woosuk.data.network.dto.UserLeagueDto
import com.woosuk.data.network.dto.UserProfileDto
import retrofit2.http.GET
import retrofit2.http.Path

interface KrService {
    @GET("/lol/summoner/v4/summoners/by-puuid/{puuid}")
    suspend fun getUserProfile(
        @Path("puuid") uid: String,
    ): ApiResponse<UserProfileDto>

    @GET("/lol/league/v4/entries/by-summoner/{summonerId}")
    suspend fun getUserLeagueInfo(
        @Path("summonerId") summonerId: String,
    ): ApiResponse<List<UserLeagueDto>>
}
