package com.woosuk.home

import com.woosuk.domain.usecase.GetCurrentUserMatchListUseCase
import com.woosuk.domain.usecase.GetCurrentUserUseCase
import com.woosuk.domain.usecase.LogoutUseCase
import com.woosuk.idlingtest.CountingIdlingResource
import com.woosuk.test.MainDispatcherRule
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

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
