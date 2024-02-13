package com.woosuk.home

import com.woosuk.domain.model.User

sealed class UserUiState {
    data object Loading : UserUiState()
    data object Error : UserUiState()
    data class Success(val user: User) : UserUiState()
}
