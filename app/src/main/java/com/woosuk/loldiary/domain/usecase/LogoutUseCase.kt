package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserAccountUseCase: GetCurrentUserAccountUseCase
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val currentUser = getCurrentUserAccountUseCase()
            ?: throw IllegalStateException("로그인을 하지 않은 상태로 로그아웃을 할 수 없어요")
        userRepository.updateUserAccount(currentUser, false)
    }
}
