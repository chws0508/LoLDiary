package com.woosuk.main

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder.ofFloat
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.woosuk.home.navigation.HOME_ROUTE
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme
import com.woosuk.onboarding.navigation.ONBOARDING_ROUTE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val scaleX = ofFloat(View.SCALE_X, 1f, 5f, 1f)
            val scaleY = ofFloat(View.SCALE_Y, 1f, 5f, 1f)
            ObjectAnimator.ofPropertyValuesHolder(splashScreenView.iconView, scaleX, scaleY)
                .run {
                    interpolator = AnticipateInterpolator()
                    duration = 1000L
                    doOnEnd {
                        splashScreenView.remove()
                    }
                    start()
                }
        }

        lifecycleScope.launch {
            mainViewModel.isOnboarded.collect { isOnboarded ->
                when (isOnboarded) {
                    true -> composeStart(HOME_ROUTE)
                    false -> composeStart(ONBOARDING_ROUTE)
                    else -> {}
                }
            }
        }
    }

    private fun ComponentActivity.composeStart(startDestination: String) {
        setContent {
            LoLDiaryTheme {
                LoLDiaryApp(startDestination = startDestination)
            }
        }
    }
}
