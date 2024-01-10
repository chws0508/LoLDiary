package com.woosuk.loldiary.ui.home

import com.woosuk.loldiary.domain.model.UserMatchInfo

sealed class MatchUiState {
    data object Loading : MatchUiState()
    class Error(val message: String?) : MatchUiState()
    data class Success(val userMatchInfoList: List<UserMatchInfo>) : MatchUiState()
}
