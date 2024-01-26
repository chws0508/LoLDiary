package com.woosuk.loldiary

import com.woosuk.loldiary.domain.usecase.GetCurrentUserAccountUseCase
import com.woosuk.loldiary.domain.usecase.GetCurrentUserUseCase
import com.woosuk.loldiary.domain.usecase.LogoutUseCase
import com.woosuk.loldiary.ui.home.HomeViewModel
import com.woosuk.loldiary.ui.main.MainViewModel
import com.woosuk.loldiary.ui.utils.CountingIdlingResource
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val dispatcherRul = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel

    private lateinit var logoutUseCase: LogoutUseCase
    private lateinit var getCurrentUserAccountUseCase: GetCurrentUserAccountUseCase

    @Before
    fun setUp() {
        getCurrentUserAccountUseCase = mockk<GetCurrentUserAccountUseCase>(relaxed = true)
        logoutUseCase = mockk<LogoutUseCase>(relaxed = true)
    }

    @Test
    fun `로그 아웃할 수 있다`() = runTest {
        // when
        viewModel = MainViewModel(
            getCurrentUserAccountUseCase = getCurrentUserAccountUseCase,
            logoutUseCase = logoutUseCase,
        )
        viewModel.logout()
        // then
        coVerify { logoutUseCase.invoke() }
    }
}
