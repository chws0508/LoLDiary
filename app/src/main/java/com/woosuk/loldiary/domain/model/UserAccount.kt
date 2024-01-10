package com.woosuk.loldiary.domain.model

data class UserAccount(
    val uid: String,
    val summonerId: String,
    val gameName: String,
    val tageLine: String,
    val isCurrentUser: Boolean,
) {
    companion object {
        fun mock(): UserAccount = UserAccount(
            uid = "mock uid",
            summonerId = "summoner Id",
            gameName = "박보영",
            tageLine = "0508",
            isCurrentUser = false,
        )
    }
}
