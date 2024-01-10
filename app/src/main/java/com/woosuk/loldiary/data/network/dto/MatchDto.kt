package com.woosuk.loldiary.data.network.dto

import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.model.UserMatchInfo
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

fun MatchDto.toUserMatchInfo(puuid: String): UserMatchInfo {
    val participant = info.participants.find { it.puuid == puuid } ?: throw IllegalStateException()
    return UserMatchInfo(
        userAccount = UserAccount(
            uid = participant.puuid,
            summonerId = participant.summonerId,
            gameName = participant.riotIdGameName,
            tageLine = participant.riotIdTagline,
            isCurrentUser = false,
        ),
        gameDuration = info.gameDuration,
        gameEndTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(info.gameEndTimestamp),
            TimeZone.getDefault().toZoneId(),
        ),
        spell1Id = participant.summoner1Id,
        spell2Id = participant.summoner2Id,
        isWin = participant.win,
        championId = participant.championId,
        matchType = info.gameType,
        itemList = listOf(
            participant.item0,
            participant.item1,
            participant.item2,
            participant.item3,
            participant.item4,
            participant.item5,
            participant.item6,
        ),
        gameId = info.gameId,
        championName = participant.championName,
        kills = participant.kills,
        deaths = participant.deaths,
        assists = participant.assists,
    )
}

@Serializable
data class MatchDto(
    val metadata: MetaDataDto,
    val info: InfoDto,
)

@Serializable
data class MetaDataDto(
    val dataVersion: String,
    val matchId: String,
    val participants: List<String>,
)

@Serializable
data class InfoDto(
    val gameId: Long,
    val gameType: String,
    val gameDuration: Long,
    val gameEndTimestamp: Long,
    val participants: List<ParticipantDto>,
)

@Serializable
data class TeamDto(
    val win: Boolean,
    val teamId: Int,
    val participants: List<ParticipantDto>,
)

@Serializable
data class ParticipantDto(
    val puuid: String,
    val summonerId: String,
    val riotIdGameName: String,
    val riotIdTagline: String,
    val kills: Int,
    val assists: Int,
    val deaths: Int,
    val champLevel: Int,
    val championId: Int,
    val championName: String,
    val item0: Int,
    val item1: Int,
    val item2: Int,
    val item3: Int,
    val item4: Int,
    val item5: Int,
    val item6: Int,
    val summoner1Id: Int,
    val summoner2Id: Int,
    val win: Boolean,
)
