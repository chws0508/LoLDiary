package com.woosuk.loldiary

import com.woosuk.loldiary.domain.usecase.GetCurrentUserMatchListUseCase
import com.woosuk.loldiary.domain.usecase.GetCurrentUserUseCase
import com.woosuk.loldiary.domain.usecase.LogoutUseCase
import com.woosuk.loldiary.ui.home.HomeViewModel
import com.woosuk.loldiary.ui.utils.CountingIdlingResource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val dispatcherRul = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase
    private lateinit var getCurrentUserMatchListUseCase: GetCurrentUserMatchListUseCase
    private lateinit var logoutUseCase: LogoutUseCase
    private lateinit var countingIdlingResource: CountingIdlingResource

    @Before
    fun setUp() {
        getCurrentUserUseCase = mockk<GetCurrentUserUseCase>(relaxed = true)
        getCurrentUserMatchListUseCase = mockk<GetCurrentUserMatchListUseCase>(relaxed = true)
        logoutUseCase = mockk<LogoutUseCase>(relaxed = true)
        countingIdlingResource = mockk<CountingIdlingResource>(relaxed = true)
    }
}
