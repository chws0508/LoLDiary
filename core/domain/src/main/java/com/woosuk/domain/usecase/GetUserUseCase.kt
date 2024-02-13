package com.woosuk.domain.usecase

import com.woosuk.domain.model.User
import com.woosuk.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        gameName: String,
        tagLine: String,

        onError: (String) -> Unit,
    ): Flow<User> = userRepository.getUser(
        gameName = gameName,
        tageLine = tagLine,
    ) { onError(it) }
}
