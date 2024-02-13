package com.woosuk.data.network.dto

import com.woosuk.domain.model.UserProfile
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val id: String,
    val accountId: String,
    val puuid: String,
    val name: String,
    val profileIconId: Int,
    val revisionDate: Long,
    val summonerLevel: Int,
)

fun UserProfileDto.toDomain() = UserProfile(
    level = summonerLevel,
    profileImageId = profileIconId,
)
