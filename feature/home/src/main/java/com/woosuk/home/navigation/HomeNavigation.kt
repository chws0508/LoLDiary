package com.woosuk.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.home.HomeRoute

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    navigateToOnboarding: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(navigateToOnboarding = navigateToOnboarding)
    }
}
