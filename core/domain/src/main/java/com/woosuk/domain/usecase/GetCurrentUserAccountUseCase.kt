package com.woosuk.domain.usecase

import com.woosuk.domain.model.UserAccount
import com.woosuk.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

class GetCurrentUserAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): UserAccount? = userRepository.getCurrentUserAccount()
}
