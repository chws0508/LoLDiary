package com.woosuk.loldiary.domain.model

import java.time.LocalDateTime

data class UserMatchInfo(
    val userAccount: UserAccount,
    val gameId: Long,
    val gameDuration: Long,
    val gameEndTime: LocalDateTime,
    val spell1Id: Int,
    val spell2Id: Int,
    val isWin: Boolean,
    val championId: Int,
    val championName: String,
    val matchType: String,
    val itemList: List<Int>,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
) {
    val itemImageUrls = itemList.map { ITEM_IMAGE_PREFIX + it + IMAGE_TYPE }
    val championUrl = CHAMPION_IMAGE_PREFIX + championName + IMAGE_TYPE
    val spell1Url = SPELL_IMAGE_PREFIX + Spell.fromId(spell1Id).name + IMAGE_TYPE
    val spell2Url = SPELL_IMAGE_PREFIX + Spell.fromId(spell2Id).name + IMAGE_TYPE

    companion object {
        private const val ITEM_IMAGE_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/"

        private const val CHAMPION_IMAGE_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/"

        private const val SPELL_IMAGE_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/spell/"
        private const val IMAGE_TYPE = ".png"
    }
}

enum class Spell(val id: Int) {
    SummonerBarrier(21),
    SummonerBoost(1),
    SummonerCherryFlash(2202),
    SummonerCherryHold(2201),
    SummonerDot(14),
    SummonerExhaust(3),
    SummonerFlash(4),
    SummonerHaste(6),
    SummonerHeal(7),
    SummonerMana(13),
    SummonerPoroRecall(30),
    SummonerPoroThrow(31),
    SummonerSmite(11),
    SummonerSnowURFSnowball_Mark(39),
    SummonerSnowball(32),
    SummonerTeleport(12),
    Summoner_UltBookPlaceholder(54),
    Summoner_UltBookSmitePlaceholder(5),
    ;

    companion object {
        fun fromId(id: Int): Spell {
            return values().find { it.id == id } ?: Summoner_UltBookPlaceholder
        }
    }
}
