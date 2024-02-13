package com.woosuk.domain.usecase

import com.woosuk.domain.model.UserAccount
import com.woosuk.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class GetPreviousUserAccountsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<UserAccount> = withContext(Dispatchers.IO) {
        userRepository.getPreviousUserAccounts()
    }
}
