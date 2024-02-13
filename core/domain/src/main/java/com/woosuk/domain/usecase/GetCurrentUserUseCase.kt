package com.woosuk.domain.usecase

import com.woosuk.domain.model.User
import com.woosuk.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        onError: (String) -> Unit,
    ): Flow<User> = flow {
        val userAccount = userRepository.getCurrentUserAccount() ?: throw IllegalStateException()
        emit(
            userRepository.getUser(
                userAccount.gameName,
                userAccount.tageLine,
            ) { onError(it) }.first(),
        )
    }.flowOn(Dispatchers.IO)
}
