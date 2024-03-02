package com.woosuk.main

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.woosuk.blacklist.navigation.BLACK_LIST_ROUTE
import com.woosuk.blacklist.navigation.blackListScreen
import com.woosuk.blacklist.navigation.navigateToBlackList
import com.woosuk.designsystem.R.*
import com.woosuk.diary.navigation.DIARY_ROUTE
import com.woosuk.diary.navigation.diaryScreen
import com.woosuk.diary.navigation.navigateToDiary
import com.woosuk.home.navigation.HOME_ROUTE
import com.woosuk.home.navigation.homeScreen
import com.woosuk.home.navigation.navigateToHome
import com.woosuk.main.BottomTab.BLACKLIST
import com.woosuk.main.BottomTab.DIARY
import com.woosuk.main.BottomTab.HOME
import com.woosuk.onboarding.navigation.ONBOARDING_ROUTE
import com.woosuk.onboarding.navigation.navigateToOnboarding
import com.woosuk.onboarding.navigation.onboardingScreen

@Composable
fun LoLDiaryApp(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = hiltViewModel(),
    startDestination: String,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val clearAllStackOptions = navOptions { popUpTo(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentDestination?.route != ONBOARDING_ROUTE) {
                MainTopBar(navigateToOnBoarding = {
                    mainViewModel.logout()
                    navController.navigateToOnboarding(
                        clearAllStackOptions,
                    )
                })
            }
        },
        bottomBar = {
            if (currentDestination?.route != ONBOARDING_ROUTE) {
                MainBottomBar(
                    currentDestination = currentDestination ?: return@Scaffold,
                    navController = navController,
                )
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                homeScreen { navController.navigateToOnboarding(clearAllStackOptions) }
                onboardingScreen { navController.navigateToHome(clearAllStackOptions) }
                diaryScreen()
                blackListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navigateToOnBoarding: () -> Unit,
    title: String = stringResource(id = string.app_name),
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        },
        actions = {
            Text(
                text = "로그아웃",
                modifier =
                Modifier
                    .clickable { navigateToOnBoarding() }
                    .padding(end = 10.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        },
        colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = Color.Black,
        ),
    )
}

@Composable
fun MainBottomBar(
    currentDestination: NavDestination,
    navController: NavController,
) {
    val context = LocalContext.current
    NavigationBar {
        BottomTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = currentDestination.route == tab.route,
                onClick = { navController.navigateToTabScreen(tab) },
                icon = {
                    Icon(
                        imageVector = tab.iconImageVector,
                        contentDescription = null,
                    )
                },
                label = { Text(text = context.getString(tab.titleResource)) },
            )
        }
    }
}

fun NavController.navigateToTabScreen(bottomTab: BottomTab) {
    val tabNavOptions =
        navOptions {
            popUpTo(HOME_ROUTE) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    when (bottomTab) {
        HOME -> navigateToHome(tabNavOptions)
        DIARY -> navigateToDiary(tabNavOptions)
        BLACKLIST -> navigateToBlackList(tabNavOptions)
    }
}

enum class BottomTab(
    val route: String,
    val iconImageVector: ImageVector,
    @StringRes val titleResource: Int,
) {
    HOME(
        route = HOME_ROUTE,
        iconImageVector = Icons.Filled.Home,
        titleResource =
        R.string.tab_home_title,
    ),
    DIARY(
        route = DIARY_ROUTE,
        iconImageVector = Icons.Filled.Edit,
        titleResource = R.string.tab_diary_title,
    ),
    BLACKLIST(
        route = BLACK_LIST_ROUTE,
        iconImageVector = Icons.Filled.Person,
        titleResource = R.string.tab_black_list_title,
    ),
}
