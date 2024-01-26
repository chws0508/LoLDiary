package com.woosuk.loldiary

import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.usecase.GetPreviousUserAccountsUseCase
import com.woosuk.loldiary.domain.usecase.GetUserUseCase
import com.woosuk.loldiary.domain.usecase.SaveUserUseCase
import com.woosuk.loldiary.ui.onboarding.OnboardingViewModel
import com.woosuk.loldiary.ui.utils.CountingIdlingResource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class OnboardingViewModelTest {

    @get:Rule
    val dispatcherRul = MainDispatcherRule()

    private lateinit var viewModel: OnboardingViewModel

    private lateinit var getPreviousUserAccountsUseCase: GetPreviousUserAccountsUseCase
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var saveUserUseCase: SaveUserUseCase
    private lateinit var countingIdlingResource: CountingIdlingResource

    @Before
    fun setUp() {
        getPreviousUserAccountsUseCase = mockk<GetPreviousUserAccountsUseCase>()
        getUserUseCase = mockk<GetUserUseCase>()
        saveUserUseCase = mockk<SaveUserUseCase>()
        countingIdlingResource = mockk<CountingIdlingResource>()
    }

    @Test
    fun `과거에 로그인 했던 유저 계정 정보 리스트를 가져온다`() = runTest {
        // given
        coEvery { getPreviousUserAccountsUseCase.invoke() } returns UserAccount.mockList()

        // when
        viewModel = OnboardingViewModel(
            getUserUseCase = getUserUseCase,
            saveUserUseCase = saveUserUseCase,
            getPreviousUserAccountUseCase = getPreviousUserAccountsUseCase,
            countingIdlingResource = countingIdlingResource
        )
        // then
        coVerify { getPreviousUserAccountsUseCase.invoke() }
        assertEquals(viewModel.previousUserAccounts.value, UserAccount.mockList())
    }
}
