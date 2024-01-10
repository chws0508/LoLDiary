package com.woosuk.loldiary.data.network.dto

import com.woosuk.loldiary.data.network.dto.TierDto.BRONZE
import com.woosuk.loldiary.data.network.dto.TierDto.CHALLENGER
import com.woosuk.loldiary.data.network.dto.TierDto.DIAMOND
import com.woosuk.loldiary.data.network.dto.TierDto.EMERALD
import com.woosuk.loldiary.data.network.dto.TierDto.GOLD
import com.woosuk.loldiary.data.network.dto.TierDto.GRANDMASTER
import com.woosuk.loldiary.data.network.dto.TierDto.IRON
import com.woosuk.loldiary.data.network.dto.TierDto.MASTER
import com.woosuk.loldiary.data.network.dto.TierDto.PLATINUM
import com.woosuk.loldiary.data.network.dto.TierDto.SILVER
import com.woosuk.loldiary.domain.model.Tier
import com.woosuk.loldiary.domain.model.UserRankInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLeagueDto(
    val queueType: String,
    val tier: TierDto = TierDto.BRONZE,
    val rank: String = "",
    val leaguePoints: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
)

fun UserLeagueDto.toDomain() = UserRankInfo(
    tier = tier.toDomain(),
    rank = rank,
    points = leaguePoints,
    wins = wins,
    losses = losses,
)

@Serializable
enum class TierDto {
    @SerialName("IRON")
    IRON,

    @SerialName("BRONZE")
    BRONZE,

    @SerialName("SILVER")
    SILVER,

    @SerialName("GOLD")
    GOLD,

    @SerialName("PLATINUM")
    PLATINUM,

    @SerialName("EMERALD")
    EMERALD,

    @SerialName("DIAMOND")
    DIAMOND,

    @SerialName("MASTER")
    MASTER,

    @SerialName("GRANDMASTER")
    GRANDMASTER,

    @SerialName("CHALLENGER")
    CHALLENGER,
}

fun TierDto.toDomain() = when (this) {
    IRON -> Tier.IRON
    BRONZE -> Tier.BRONZE
    SILVER -> Tier.SILVER
    GOLD -> Tier.GOLD
    PLATINUM -> Tier.PLATINUM
    EMERALD -> Tier.EMERALD
    DIAMOND -> Tier.DIAMOND
    MASTER -> Tier.MASTER
    GRANDMASTER -> Tier.GRANDMASTER
    CHALLENGER -> Tier.CHALLENGER
}
