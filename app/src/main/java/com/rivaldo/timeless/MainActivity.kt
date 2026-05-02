package com.rivaldo.timeless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.rivaldo.timeless.presentation.core.TimelessTheme
import com.rivaldo.timeless.presentation.diary.DiaryScreen
import com.rivaldo.timeless.presentation.home.HomeScreen
import com.rivaldo.timeless.presentation.onboarding.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * Sealed class representing the possible navigation destinations.
 */
private sealed class Screen {
    data object Onboarding : Screen()
    data object Home : Screen()
    data object Diary : Screen()
}

/**
 * Main entry point for Timeless.
 * Manages top-level navigation between Onboarding, Home, and Diary screens.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimelessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isOnboarded by remember { mutableStateOf(false) }
                    var currentScreen by remember { mutableStateOf<Screen>(Screen.Onboarding) }

                    when (currentScreen) {
                        Screen.Onboarding -> {
                            OnboardingScreen(
                                onComplete = {
                                    isOnboarded = true
                                    currentScreen = Screen.Home
                                }
                            )
                        }

                        Screen.Home -> {
                            HomeScreen(
                                onDiaryClicked = { currentScreen = Screen.Diary }
                            )
                        }

                        Screen.Diary -> {
                            DiaryScreen(
                                onBackClick = { currentScreen = Screen.Home }
                            )
                        }
                    }
                }
            }
        }
    }
}
