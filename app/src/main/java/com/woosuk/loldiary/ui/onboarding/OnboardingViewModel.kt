package com.woosuk.loldiary.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.usecase.GetPreviousUserAccountsUseCase
import com.woosuk.loldiary.domain.usecase.GetUserUseCase
import com.woosuk.loldiary.domain.usecase.SaveUserUseCase
import com.woosuk.loldiary.ui.utils.CountingIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getPreviousUserAccountUseCase: GetPreviousUserAccountsUseCase,
    private val countingIdlingResource: CountingIdlingResource,
) : ViewModel() {

    private val _nickName: MutableStateFlow<String> = MutableStateFlow("")
    val nickName = _nickName.asStateFlow()

    private val _tagLine: MutableStateFlow<String> = MutableStateFlow("")
    val tagLine = _tagLine.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _onboardingEvent: MutableSharedFlow<OnboardingEvent> = MutableSharedFlow()
    val onboardingEvent = _onboardingEvent.asSharedFlow()

    private val _previousUserAccounts: MutableStateFlow<List<UserAccount>> = MutableStateFlow(
        emptyList()
    )
    val previousUserAccounts = _previousUserAccounts.asStateFlow()

    init {
        viewModelScope.launch {
            _previousUserAccounts.value = getPreviousUserAccountUseCase()
        }
    }

    fun onNickNameChanged(newValue: String) {
        _nickName.update { newValue }
    }

    fun onClearNickName() {
        _nickName.update { "" }
    }

    fun onTagLineChanged(newValue: String) {
        _tagLine.update { newValue }
    }

    fun onClearTagLine() {
        _tagLine.update { "" }
    }

    fun canCompleteOnboarding(): Boolean {
        return tagLine.value != "" && nickName.value != ""
    }

    fun completeOnboarding() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase(
                gameName = nickName.value,
                tagLine = tagLine.value,
                onError = {
                    launch { _onboardingEvent.emit(OnboardingEvent.Error(it)) }
                },
            ).onEach {
                saveUserUseCase(userAccount = it.userAccount)
                _onboardingEvent.emit(OnboardingEvent.Success)
            }.onStart {
                _isLoading.value = true
                countingIdlingResource.increment()
            }.onCompletion {
                _isLoading.value = false
                countingIdlingResource.decrement()
            }.launchIn(this)
        }
    }

    sealed class OnboardingEvent {
        data object Success : OnboardingEvent()
        class Error(val message: String) : OnboardingEvent()
    }
}
