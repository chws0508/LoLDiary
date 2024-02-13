package com.woosuk.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
) {
    val userUiState by homeViewModel.userUiState.collectAsStateWithLifecycle()
    val matchUiState by homeViewModel.matchUiState.collectAsStateWithLifecycle()
    if (userUiState is UserUiState.Error) navigateToOnboarding()
    HomeScreen(
        userUiState,
        matchUiState,
    )
}

@Composable
fun HomeScreen(
    userUiState: UserUiState,
    matchUiState: MatchUiState,
) {
    when {
        userUiState is UserUiState.Error -> Text(text = "에러 화면")
        userUiState is UserUiState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        userUiState is UserUiState.Success && matchUiState is MatchUiState.Success -> {
            LazyColumn() {
                item {
                    UserInfoCard(user = userUiState.user)
                }
                item {
                    Text(
                        text = "최근 전적",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(17.dp)
                    )
                }
                items(
                    key = { it.gameId },
                    items = matchUiState.userMatchInfoList,
                ) { item ->
                    MatchInfoItem(userMatchInfo = item)
                }
            }
        }
    }
}
