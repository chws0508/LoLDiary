package com.woosuk.loldiary.ui.home

import com.woosuk.loldiary.domain.model.User

sealed class UserUiState {
    data object Loading : UserUiState()
    data object Error : UserUiState()
    data class Success(val user: User) : UserUiState()
}
