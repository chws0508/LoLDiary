package com.woosuk.loldiary.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.loldiary.ui.onboarding.OnboardingRoute

const val ONBOARDING_ROUTE = "onboarding_route"

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) =
    navigate(ONBOARDING_ROUTE, navOptions)

fun NavGraphBuilder.onboardingScreen(
    navigateToMainScreen: () -> Unit,
) {
    composable(route = ONBOARDING_ROUTE) {
        OnboardingRoute(
            navigateToMainScreen = { navigateToMainScreen() },
        )
    }
}
