package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.model.UserMatchInfo
import com.woosuk.loldiary.domain.repository.MatchRepository
import com.woosuk.loldiary.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserMatchListUseCase @Inject constructor(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        onError: (String) -> Unit,
    ): Flow<List<UserMatchInfo>> {
        val puuid: String = userRepository.getCurrentUserAccount()?.uid ?: ""
        return matchRepository.getUserMatchInfoList(puuid) { onError(it) }
    }
}
