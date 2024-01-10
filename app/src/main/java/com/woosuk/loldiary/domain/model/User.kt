package com.woosuk.loldiary.domain.model

data class User(
    val userAccount: UserAccount,
    val userProfile: UserProfile,
    val userRankInfo: UserRankInfo,
) {
    companion object {
        fun mock() = User(
            userAccount = UserAccount(
                uid = "",
                summonerId = "",
                gameName = "박보영",
                tageLine = "0508",
                isCurrentUser = false,
            ),
            userProfile = UserProfile(
                level = 360,
                profileImageId = 580,
            ),
            userRankInfo = UserRankInfo(
                tier = Tier.DIAMOND,
                rank = "2",
                points = 100,
                wins = 50,
                losses = 50,
            ),
        )
    }
}
