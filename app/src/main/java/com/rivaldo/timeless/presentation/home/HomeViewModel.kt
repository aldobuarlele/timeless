package com.rivaldo.timeless.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.timeless.domain.usecase.CalculateTimeCanvasUseCase
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
 * ViewModel for the main Home/Zen Canvas screen.
 *
 * Collects the user profile from [GetUserProfileUseCase], passes it to
 * [CalculateTimeCanvasUseCase] to compute week data, and exposes [HomeUiState]
 * for the composable layer to consume.
 *
 * Also injects [GetLogForDateUseCase] to detect if today's log exists,
 * which can be used for dimming logic on the Zen Canvas (future).
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val calculateTimeCanvasUseCase: CalculateTimeCanvasUseCase,
    private val getLogForDateUseCase: GetLogForDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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
