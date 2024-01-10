package com.woosuk.loldiary.data.persistence.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woosuk.loldiary.domain.model.UserAccount

@Entity
data class UserAccountEntity(
    @PrimaryKey val puuid: String,
    val summonerId: String,
    val gameName: String,
    val tagLine: String,
    val isCurrentUser: Boolean,
)

fun UserAccountEntity.toDomain(): UserAccount = UserAccount(
    uid = puuid,
    gameName = gameName,
    tageLine = tagLine,
    summonerId = summonerId,
    isCurrentUser = true,
)

fun UserAccount.toEntity(): UserAccountEntity = UserAccountEntity(
    puuid = uid,
    summonerId = summonerId,
    gameName = gameName,
    tagLine = tageLine,
    isCurrentUser = true,
)
