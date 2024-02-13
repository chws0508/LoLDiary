package com.woosuk.domain.model

data class UserRankInfo(
    val tier: Tier,
    val rank: String,
    val points: Int,
    val wins: Int,
    val losses: Int,
)

enum class Tier(val value: String) {
    IRON("아이언"),
    BRONZE("브론즈"),
    SILVER("실버"),
    GOLD("골드"),
    PLATINUM("플래티넘"),
    EMERALD("에메랄드"),
    DIAMOND("다이아몬드"),
    MASTER("미스터"),
    GRANDMASTER("그랜드 마스터"),
    CHALLENGER("챌린저"),
}
