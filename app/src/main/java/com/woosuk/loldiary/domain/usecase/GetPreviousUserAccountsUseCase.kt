package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPreviousUserAccountsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<UserAccount> = withContext(Dispatchers.IO) {
        userRepository.getPreviousUserAccounts()
    }
}
