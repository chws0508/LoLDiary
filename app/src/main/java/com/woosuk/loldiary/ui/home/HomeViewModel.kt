package com.woosuk.loldiary.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.loldiary.domain.usecase.GetCurrentUserMatchListUseCase
import com.woosuk.loldiary.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getCurrentUserMatchListUseCase: GetCurrentUserMatchListUseCase,
) : ViewModel() {

    private val _userUiState: MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState.Loading)
    val userUiState = _userUiState.asStateFlow()

    private val _matchUiState: MutableStateFlow<MatchUiState> =
        MutableStateFlow(MatchUiState.Loading)
    val matchUiState = _matchUiState.asStateFlow()

    init {
        fetchUser()
        fetchMatchList()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            getCurrentUserUseCase(onError = { _userUiState.value = UserUiState.Error })
                .onEach { user -> _userUiState.value = UserUiState.Success(user) }
                .onStart { _userUiState.value = UserUiState.Loading }
                .launchIn(this)
        }
    }

    private fun fetchMatchList() {
        viewModelScope.launch {
            getCurrentUserMatchListUseCase { _matchUiState.value = MatchUiState.Error(it) }
                .onStart { _matchUiState.value = MatchUiState.Loading }
                .onEach { _matchUiState.value = MatchUiState.Success(it) }
                .launchIn(this)
        }
    }
}
