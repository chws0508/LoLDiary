package com.woosuk.domain.model

data class UserProfile(
    val level: Int,
    val profileImageId: Int,
) {
    val profileImageUrl: String = ProfileImagePrefix + profileImageId + ImageType

    companion object {
        private const val ProfileImagePrefix =
            "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/"

        private const val ImageType = ".png"
    }
}
