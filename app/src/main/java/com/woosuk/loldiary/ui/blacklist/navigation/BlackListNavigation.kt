package com.woosuk.loldiary.ui.blacklist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.loldiary.ui.blacklist.BlackListRoute

const val BLACK_LIST_ROUTE = "black_list_route"

fun NavController.navigateToBlackList(navOptions: NavOptions) =
    navigate(BLACK_LIST_ROUTE, navOptions)

fun NavGraphBuilder.blackListScreen() {
    composable(BLACK_LIST_ROUTE) {
        BlackListRoute()
    }
}
