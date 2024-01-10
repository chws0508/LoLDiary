package com.woosuk.loldiary.domain.usecase

import com.woosuk.loldiary.domain.repository.UserRepository
import javax.inject.Inject

class DeleteCurrentUserAccountUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(){

    }
}
