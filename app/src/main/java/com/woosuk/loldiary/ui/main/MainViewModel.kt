package com.woosuk.loldiary.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.loldiary.domain.usecase.GetCurrentUserAccountUseCase
import com.woosuk.loldiary.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUserAccountUseCase: GetCurrentUserAccountUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _isOnboarded: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isOnboarded = _isOnboarded.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getCurrentUserAccountUseCase()
            _isOnboarded.value = user != null
        }
    }

    fun logout() {
        viewModelScope.launch { logoutUseCase() }
    }
}
