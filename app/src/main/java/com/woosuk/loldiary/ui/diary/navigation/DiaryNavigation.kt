package com.woosuk.loldiary.ui.diary.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.loldiary.ui.diary.DiaryRoute

const val DIARY_ROUTE = "diary_route"

fun NavController.navigateToDiary(navOptions: NavOptions) = navigate(DIARY_ROUTE, navOptions)

fun NavGraphBuilder.diaryScreen() {
    composable(DIARY_ROUTE) {
        DiaryRoute()
    }
}
