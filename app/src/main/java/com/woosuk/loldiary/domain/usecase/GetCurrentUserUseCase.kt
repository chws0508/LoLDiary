package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.model.User
import com.woosuk.loldiary.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        onError: (String) -> Unit,
    ): Flow<User> = flow {
        val userAccount = userRepository.getCurrentUserAccount() ?: return@flow
        emit(
            userRepository.getUser(
                userAccount.gameName,
                userAccount.tageLine,
                false,
            ) { onError(it) }.first(),
        )
    }.flowOn(Dispatchers.IO)
}
