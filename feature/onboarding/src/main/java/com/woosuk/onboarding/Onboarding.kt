package com.woosuk.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.designsystem.R.drawable
import com.woosuk.domain.model.UserAccount
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme
import com.woosuk.onboarding.OnboardingViewModel.OnboardingEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun OnboardingRoute(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit,
) {
    val nickName by onboardingViewModel.nickName.collectAsStateWithLifecycle()
    val tagLine by onboardingViewModel.tagLine.collectAsStateWithLifecycle()
    val isLoading by onboardingViewModel.isLoading.collectAsStateWithLifecycle()
    val previousUsers by onboardingViewModel.previousUserAccounts.collectAsStateWithLifecycle()
    var onboardingEvent by remember { mutableStateOf<OnboardingEvent?>(null) }

    LaunchedEffect(Unit) {
        onboardingViewModel.onboardingEvent
            .onEach { onboardingEvent = it }
            .launchIn(this)
    }

    OnboardingScreen(
        nickName = nickName,
        tagLine = tagLine,
        isLoading = isLoading,
        onNickNameChanged = onboardingViewModel::onNickNameChanged,
        onTagLineChanged = onboardingViewModel::onTagLineChanged,
        onClearTagLine = onboardingViewModel::onClearTagLine,
        onClearNickName = onboardingViewModel::onClearNickName,
        onCompleteOnboarding = onboardingViewModel::completeOnboarding,
        canCompleteOnboarding = onboardingViewModel.canCompleteOnboarding(),
        onboardingEvent = onboardingEvent,
        navigateToMainScreen = navigateToMainScreen,
        previousUsers = previousUsers,
        onClickItem = {
            onboardingViewModel.onTagLineChanged(it.tageLine)
            onboardingViewModel.onNickNameChanged(it.gameName)
        }
    )
}

@Composable
fun OnboardingScreen(
    nickName: String = "",
    tagLine: String = "",
    previousUsers: List<UserAccount> = emptyList(),
    isLoading: Boolean = false,
    onNickNameChanged: (String) -> Unit = {},
    onTagLineChanged: (String) -> Unit = {},
    onClearTagLine: () -> Unit = {},
    onClearNickName: () -> Unit = {},
    onCompleteOnboarding: () -> Unit = {},
    canCompleteOnboarding: Boolean = false,
    onboardingEvent: OnboardingEvent? = null,
    navigateToMainScreen: () -> Unit = {},
    onClickItem: (UserAccount) -> Unit = {},
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(Modifier.fillMaxSize()) {
            Column() {
                OnboardingIntroduction()
                OnBoardingNickNameTextField(
                    nickName = nickName,
                    onNickNameChanged = onNickNameChanged,
                    onClear = onClearNickName,
                )

                OnBoardingTagLineTextField(
                    tagLine = tagLine,
                    onTagLineChanged = onTagLineChanged,
                    onClear = onClearTagLine,
                )

                OnboardingCompleteButton(
                    onClick = onCompleteOnboarding,
                    enabled = canCompleteOnboarding,
                )
                Spacer(modifier = Modifier.height(17.dp))
                OnboardingPreviousUserList(
                    previousUsers = previousUsers,
                    onClickItem = onClickItem
                )
            }

            if (isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }

    LaunchedEffect(key1 = onboardingEvent) {
        when (onboardingEvent) {
            is OnboardingEvent.Error -> Toast.makeText(
                context,
                onboardingEvent.message,
                Toast.LENGTH_SHORT,
            ).show()

            is OnboardingEvent.Success -> navigateToMainScreen()
            null -> {}
        }
    }
}

@Composable
fun OnboardingIntroduction(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(start = 17.dp, top = 17.dp, bottom = 10.dp),
    ) {
        Image(
            modifier = Modifier.padding(bottom = 10.dp),
            painter = painterResource(id = drawable.app_logo),
            contentDescription = "앱 아이콘",
        )
        Text(
            text = "내 소환사를 등록하세요!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun OnBoardingNickNameTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(17.dp),
    nickName: String,
    onNickNameChanged: (String) -> Unit,
    onClear: () -> Unit,
) {
    OnboardingTextField(
        modifier = modifier,
        leadingIcon = Icons.Filled.Person,
        hint = stringResource(R.string.nickname),
        text = nickName,
        onTextChanged = { onNickNameChanged(it) },
        onClear = { onClear() },
    )
}

@Composable
fun OnBoardingTagLineTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(17.dp),
    tagLine: String,
    onTagLineChanged: (String) -> Unit,
    onClear: () -> Unit,
) {
    OnboardingTextField(
        modifier = modifier,
        leadingIcon = Icons.Filled.Tag,
        hint = stringResource(R.string.tag),
        text = tagLine,
        onTextChanged = { onTagLineChanged(it) },
        onClear = { onClear() },
    )
}

@Composable
fun OnboardingTextField(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector,
    hint: String,
    text: String,
    onTextChanged: (String) -> Unit,
    onClear: () -> Unit,
) {
    OutlinedTextField(
        keyboardActions = KeyboardActions.Default,
        value = text,
        onValueChange = { onTextChanged(it) },
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = { Text(text = hint, color = Color.LightGray) },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        trailingIcon = {
            if (text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "clear button",
                    modifier = Modifier.clickable {
                        onClear()
                    },
                )
            }
        },
        singleLine = true,
        modifier = modifier,
    )
}

@Composable
fun OnboardingCompleteButton(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(17.dp),
    onClick: () -> Unit,
    enabled: Boolean,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(5.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.White,
        ),
    ) {
        Text(text = stringResource(R.string.onboarding_complete), textAlign = TextAlign.Center)
    }
}

@Composable
fun OnboardingPreviousUserList(
    previousUsers: List<UserAccount>,
    onClickItem: (UserAccount) -> Unit,
    modifier: Modifier = Modifier.padding(start = 17.dp)
) {
    Column(modifier = modifier) {
        Text(
            text = "최근 로그인 기록",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            items(
                items = previousUsers,
            ) { user ->
                Text(
                    text = user.gameName + "#" + user.tageLine,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        onClickItem(user)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview(){
    LoLDiaryTheme {
        OnboardingScreen(previousUsers = listOf(UserAccount(
            uid = "",
            summonerId = "",
            gameName = "박보영",
            tageLine = "0508",
            isCurrentUser = false
        )))
    }
}

@Preview
@Composable
fun NickNameTextFieldPreview() {
    LoLDiaryTheme {
        Column(
            modifier = Modifier
                .background(Color.White),
        ) {
            OnBoardingNickNameTextField(nickName = "", onNickNameChanged = {}) {
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingCompleteButtonPreview() {
    LoLDiaryTheme {
        Column(
            modifier = Modifier
                .background(Color.White),
        ) {
            OnboardingCompleteButton(onClick = {}, enabled = true)
        }
    }
}

@Preview
@Composable
fun OnBoardingIntroductionPreview() {
    LoLDiaryTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            OnboardingIntroduction()
        }
    }
}
