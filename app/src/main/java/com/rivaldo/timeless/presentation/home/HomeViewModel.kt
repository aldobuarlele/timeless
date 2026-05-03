package com.rivaldo.timeless.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.timeless.domain.model.DailyLog
import com.rivaldo.timeless.domain.model.JourneyReflectionResult
import com.rivaldo.timeless.domain.usecase.CalculateTimeCanvasUseCase
import com.rivaldo.timeless.domain.usecase.GetJourneyReflectionUseCase
import com.rivaldo.timeless.domain.usecase.GetLogForDateUseCase
import com.rivaldo.timeless.domain.usecase.GetUserProfileUseCase
import com.rivaldo.timeless.domain.usecase.TimeCanvasData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the possible states of the Home UI.
 */
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val canvasData: TimeCanvasData) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

/**
 * UI state for the selected week's Journey Reflection bottom sheet.
 */
data class SelectedWeekState(
    val isVisible: Boolean = false,
    val weekIndex: Int = -1,
    val weekStartDate: String = "",
    val weekEndDate: String = "",
    val logs: List<DailyLog> = emptyList(),
    val isLoading: Boolean = false
)

/**
 * ViewModel for the main Home/Zen Canvas screen.
 *
 * Collects the user profile from [GetUserProfileUseCase], passes it to
 * [CalculateTimeCanvasUseCase] to compute week data, and exposes [HomeUiState]
 * for the composable layer to consume.
 *
 * Handles Journey Reflection via [GetJourneyReflectionUseCase] when the user
 * taps on a past (SPENT) dot on the Zen Canvas.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val calculateTimeCanvasUseCase: CalculateTimeCanvasUseCase,
    private val getLogForDateUseCase: GetLogForDateUseCase,
    private val getJourneyReflectionUseCase: GetJourneyReflectionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedWeekState = MutableStateFlow(SelectedWeekState())
    val selectedWeekState: StateFlow<SelectedWeekState> = _selectedWeekState.asStateFlow()

    init {
        loadCanvasData()
    }

    /**
     * Refresh the canvas data (e.g., when returning from diary or on resume).
     */
    fun refresh() {
        _uiState.update { HomeUiState.Loading }
        loadCanvasData()
    }

    /**
     * Called when a past (SPENT) dot on the Zen Canvas is clicked.
     * Loads the journal entries for that week and shows the reflection bottom sheet.
     */
    fun onDotClicked(weekIndex: Int) {
        viewModelScope.launch {
            try {
                val profile = getUserProfileUseCase().first()
                if (profile != null) {
                    _selectedWeekState.update {
                        it.copy(
                            isVisible = true,
                            weekIndex = weekIndex,
                            isLoading = true
                        )
                    }

                    getJourneyReflectionUseCase(weekIndex, profile.birthDate)
                        .first { result ->
                            _selectedWeekState.update {
                                it.copy(
                                    weekStartDate = result.weekStartDate,
                                    weekEndDate = result.weekEndDate,
                                    logs = result.logs,
                                    isLoading = false
                                )
                            }
                            true // take only first emission
                        }
                }
            } catch (e: Exception) {
                // If loading fails, dismiss the reflection
                _selectedWeekState.update {
                    SelectedWeekState()
                }
            }
        }
    }

    /**
     * Dismiss the reflection bottom sheet and reset state.
     */
    fun dismissReflection() {
        _selectedWeekState.update { SelectedWeekState() }
    }

    private fun loadCanvasData() {
        viewModelScope.launch {
            try {
                val profile = getUserProfileUseCase().first()
                if (profile != null) {
                    val canvasData = calculateTimeCanvasUseCase(profile)
                    _uiState.update { HomeUiState.Success(canvasData) }
                } else {
                    _uiState.update {
                        HomeUiState.Error(
                            "Profil belum lengkap. Silakan selesaikan pendaftaran."
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    HomeUiState.Error("Gagal memuat data: ${e.message}")
                }
            }
        }
    }
}
