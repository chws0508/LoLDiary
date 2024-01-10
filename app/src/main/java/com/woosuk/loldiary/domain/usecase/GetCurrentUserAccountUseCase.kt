package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): UserAccount? = userRepository.getCurrentUserAccount()
}
