package com.woosuk.loldiary

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.IdlingRegistry
import com.woosuk.loldiary.di.PersistenceModule
import com.woosuk.loldiary.ui.home.navigation.HOME_ROUTE
import com.woosuk.loldiary.ui.main.LoLDiaryApp
import com.woosuk.loldiary.ui.main.MainActivity
import com.woosuk.loldiary.ui.onboarding.OnboardingRoute
import com.woosuk.loldiary.ui.onboarding.OnboardingViewModel
import com.woosuk.loldiary.ui.onboarding.navigation.ONBOARDING_ROUTE
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme
import com.woosuk.loldiary.ui.utils.CountingIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase.assertEquals
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(
    PersistenceModule::class
)
class OnBoardingScreenTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeHiltTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Inject
    lateinit var idlingResource: CountingIdlingResource

    @Before
    fun setup() {
        hiltTestRule.inject()
        IdlingRegistry.getInstance().register(idlingResource.countingIdlingResource)
        composeHiltTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            LoLDiaryTheme {
                LoLDiaryApp(startDestination = ONBOARDING_ROUTE, navController = navController)
            }
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource.countingIdlingResource)
    }


    @Test
    fun 내_소환사를_등록하세요_문구가_보여지고_앱_아이콘이_보여진다() {
        // then
        composeHiltTestRule.onNodeWithText("내 소환사를 등록하세요!").assertIsDisplayed()
        composeHiltTestRule.onNodeWithContentDescription("앱 아이콘").assertIsDisplayed()
    }

    @Test
    fun 닉네임을_입력하기_전에_닉네임_힌트가_보여진다() {
        // then
        composeHiltTestRule.onNodeWithText("닉네임").assertIsDisplayed()
    }

    @Test
    fun 닉네임을_입력하면_힌트가_안보이고_텍스트가_보인다() {
        // then
        composeHiltTestRule.onNodeWithText("닉네임").performTextInput("박보영")
        composeHiltTestRule.onNodeWithText("박보영").assertIsDisplayed()
        composeHiltTestRule.onNodeWithText("닉네임").assertDoesNotExist()
    }

    @Test
    fun 태그라인을_입력하기_전에_태그_힌트가_보여진다() {
        // then
        composeHiltTestRule.onNodeWithText("태그").assertIsDisplayed()
    }

    @Test
    fun 태그라인을_입력하면_태그_힌트가_안보이고_태그가_보인다() {
        // then
        composeHiltTestRule.onNodeWithText("태그").performTextInput("0508")
        composeHiltTestRule.onNodeWithText("0508").assertIsDisplayed()
        composeHiltTestRule.onNodeWithText("태그").assertDoesNotExist()
    }

    @Test
    fun 닉네임칸에_텍스트를_입력하고_x버튼을_누르면_텍스트가_사라진고_닉네임힌트가_보여진다() {
        // then
        composeHiltTestRule.onNodeWithText("닉네임").performTextInput("박보영")
        composeHiltTestRule.onAllNodesWithContentDescription("clear button")[0].performClick()
        composeHiltTestRule.onNodeWithText("닉네임").assertIsDisplayed()
    }

    @Test
    fun 닉네임칸과_태그중_하나라도_입력하지_않으면_버튼의_클릭이_비활성화돤다() {
        // then
        composeHiltTestRule.onNodeWithText("완료").assertIsNotEnabled()
    }

    @Test
    fun 닉네임칸과_태그가_모두_입력되면_버튼의_클릭이_활성화된다() {
        // when
        composeHiltTestRule.activity.setContent {
            OnboardingRoute(
                onboardingViewModel = composeHiltTestRule.activity.viewModels<OnboardingViewModel>().value,
                navigateToMainScreen = {})
        }

        composeHiltTestRule.onNodeWithText("닉네임").performTextInput("박보영")
        composeHiltTestRule.onNodeWithText("태그").performTextInput("0508")

        // then
        composeHiltTestRule.onNodeWithText("완료")
            .assertIsEnabled()
            .assertHasClickAction()
    }

    @Test
    fun 닉네임칸과_태그를_모두_입력하고_완료버튼을_누르면_홈_화면으로_이동한다() {
        // when
        composeHiltTestRule.onNodeWithText("닉네임").performTextInput("박보영")
        composeHiltTestRule.onNodeWithText("태그").performTextInput("0508")

        // then
        composeHiltTestRule.onNodeWithText("완료")
            .assertIsEnabled()
            .assertHasClickAction()
            .performClick()

        composeHiltTestRule.onNodeWithText("LoL Diary").assertIsDisplayed()
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(HOME_ROUTE, route)
    }

    @Test
    fun 닉네임칸과_태그를_모두_입력하고_완료버튼을_누르고_홈_화면으로_이동한_후_로그아웃을_누르면_이전_로그인_기록이_나타난다() {
        // when
        composeHiltTestRule.onNodeWithText("닉네임").performTextInput("박보영")
        composeHiltTestRule.onNodeWithText("태그").performTextInput("0508")
        composeHiltTestRule.onNodeWithText("완료").performClick()
        composeHiltTestRule.onNodeWithText("로그아웃").performClick()

        // then
        composeHiltTestRule.onNodeWithText("박보영").assertIsDisplayed()
    }

    @Test
    fun 이전_로그인_기록을_클릭하면_텍스트_필드에_입력이_된다() {
        // when
        composeHiltTestRule.onNodeWithText("닉네임").performTextInput("박보영")
        composeHiltTestRule.onNodeWithText("태그").performTextInput("0508")
        composeHiltTestRule.onNodeWithText("완료").performClick()
        composeHiltTestRule.onNodeWithText("로그아웃").performClick()
        composeHiltTestRule.onNodeWithText("박보영").performClick()

        // then
        composeHiltTestRule.onNodeWithText("닉네임").assertDoesNotExist()
        composeHiltTestRule.onNodeWithText("태그").assertDoesNotExist()
        composeHiltTestRule.onAllNodesWithText("박보영")[0].assertIsDisplayed()
        composeHiltTestRule.onAllNodesWithText("0508")[0].assertIsDisplayed()
    }
}

