package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        userAccount: UserAccount,
    ) = userRepository.saveCurrentUser(userAccount)
}
