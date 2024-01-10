package com.woosuk.loldiary.ui.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.woosuk.loldiary.R
import com.woosuk.loldiary.ui.onboarding.OnboardingViewModel.OnboardingEvent
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme
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
    )
}

@Composable
fun OnboardingScreen(
    nickName: String,
    tagLine: String,
    isLoading: Boolean,
    onNickNameChanged: (String) -> Unit,
    onTagLineChanged: (String) -> Unit,
    onClearTagLine: () -> Unit,
    onClearNickName: () -> Unit,
    onCompleteOnboarding: () -> Unit,
    canCompleteOnboarding: Boolean,
    onboardingEvent: OnboardingEvent?,
    navigateToMainScreen: () -> Unit,
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(Modifier.fillMaxSize()) {
            Column() {
                OnboardingIntroduction()
                NickNameTextField(
                    nickName = nickName,
                    onNickNameChanged = onNickNameChanged,
                    onClear = onClearNickName,
                )

                TagLineTextField(
                    tagLine = tagLine,
                    onTagLineChanged = onTagLineChanged,
                    onClear = onClearTagLine,
                )

                OnboardingCompleteButton(
                    onClick = onCompleteOnboarding,
                    enabled = canCompleteOnboarding,
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
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
        )
        Text(
            text = "내 소환사를 등록하세요!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun NickNameTextField(
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
fun TagLineTextField(
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
                    contentDescription = null,
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

@Preview
@Composable
fun NickNameTextFieldPreview() {
    LoLDiaryTheme {
        Column(
            modifier = Modifier
                .background(Color.White),
        ) {
            NickNameTextField(nickName = "", onNickNameChanged = {}) {
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
