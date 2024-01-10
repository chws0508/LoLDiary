package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.model.User
import com.woosuk.loldiary.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        gameName: String,
        tagLine: String,
        isCurrentUser: Boolean = false,
        onError: (String) -> Unit,
    ): Flow<User> = userRepository.getUser(
        gameName = gameName,
        tageLine = tagLine,
        isCurrentUser = isCurrentUser,
    ) { onError(it) }
}
