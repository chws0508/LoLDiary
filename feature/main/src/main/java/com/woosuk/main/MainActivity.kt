package com.woosuk.main

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.lifecycle.lifecycleScope
import com.woosuk.home.navigation.HOME_ROUTE
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme
import com.woosuk.onboarding.navigation.ONBOARDING_ROUTE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {

        }else{
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1F, 0F).apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 200L
                    doOnEnd { splashScreenView.remove() }
                }.start()
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
