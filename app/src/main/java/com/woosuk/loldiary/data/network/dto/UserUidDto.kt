package com.woosuk.loldiary.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserUidDto(
    val puuid: String,
    val gameName: String,
    val tagLine: String,
)
