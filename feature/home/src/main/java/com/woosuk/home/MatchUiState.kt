package com.woosuk.home

import com.woosuk.domain.model.UserMatchInfo

sealed class MatchUiState {
    data object Loading : MatchUiState()
    class Error(val message: String?) : MatchUiState()
    data class Success(val userMatchInfoList: List<UserMatchInfo>) : MatchUiState()
}
