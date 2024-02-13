package com.woosuk.domain.model

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

        fun mockList(): List<UserAccount> = listOf(
            UserAccount(
                uid = "1",
                summonerId = "",
                gameName = "박보영",
                tageLine = "0508",
                isCurrentUser = false
            ),
            UserAccount(
                uid = "2",
                summonerId = "",
                gameName = "제드대마왕",
                tageLine = "KR1",
                isCurrentUser = false
            )
        )
    }
}
