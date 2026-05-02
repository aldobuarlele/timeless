package com.rivaldo.timeless.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.timeless.domain.model.UserProfile
import com.rivaldo.timeless.domain.usecase.GetUserProfileUseCase
import com.rivaldo.timeless.domain.usecase.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the onboarding flow.
 * Tracks all input fields and the current step (1-3).
 */
data class OnboardingUiState(
    val name: String = "",
    val birthDate: Long? = null,
    val country: String = "",
    val lifeExpectancy: Float = 70.0f,
    val homeLatitude: Double = -6.200000,
    val homeLongitude: Double = 106.816666,
    val reminderTime: String = "19:00",
    val currentStep: Int = 1,
    val isLoading: Boolean = false,
    val isComplete: Boolean = false,
    val hasExistingProfile: Boolean = false,
    val errorMessage: String? = null
)

/**
 * ViewModel for the onboarding flow.
 * Manages multi-step form state and communicates with domain layer.
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        checkExistingProfile()
    }

    private fun checkExistingProfile() {
        viewModelScope.launch {
            val profile = getUserProfileUseCase().first()
            if (profile != null) {
                _uiState.update { it.copy(hasExistingProfile = true) }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, errorMessage = null) }
    }

    fun onBirthDateChange(birthDate: Long) {
        _uiState.update { it.copy(birthDate = birthDate, errorMessage = null) }
    }

    fun onCountryChange(country: String) {
        _uiState.update { it.copy(country = country, errorMessage = null) }
    }

    fun onLifeExpectancyChange(lifeExpectancy: Float) {
        _uiState.update { it.copy(lifeExpectancy = lifeExpectancy) }
    }

    fun onSetHomeLocation() {
        // Using dummy coordinates for Jakarta, Indonesia
        _uiState.update {
            it.copy(
                homeLatitude = -6.200000,
                homeLongitude = 106.816666
            )
        }
    }

    fun onReminderTimeChange(reminderTime: String) {
        _uiState.update { it.copy(reminderTime = reminderTime) }
    }

    fun onNextStep() {
        val currentState = _uiState.value
        when (currentState.currentStep) {
            1 -> {
                if (currentState.name.isBlank()) {
                    _uiState.update { it.copy(errorMessage = "Nama tidak boleh kosong") }
                    return
                }
            }
            2 -> {
                if (currentState.birthDate == null) {
                    _uiState.update { it.copy(errorMessage = "Silakan pilih tanggal lahir") }
                    return
                }
                if (currentState.country.isBlank()) {
                    _uiState.update { it.copy(errorMessage = "Negara tidak boleh kosong") }
                    return
                }
            }
        }
        _uiState.update { it.copy(currentStep = currentState.currentStep + 1, errorMessage = null) }
    }

    fun onPreviousStep() {
        val currentStep = _uiState.value.currentStep
        if (currentStep > 1) {
            _uiState.update { it.copy(currentStep = currentStep - 1, errorMessage = null) }
        }
    }

    fun saveProfile() {
        val state = _uiState.value
        if (state.birthDate == null) {
            _uiState.update { it.copy(errorMessage = "Tanggal lahir belum diisi") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val profile = UserProfile(
                    id = java.util.UUID.randomUUID().toString(),
                    name = state.name.trim(),
                    birthDate = state.birthDate,
                    country = state.country.trim(),
                    lifeExpectancy = state.lifeExpectancy,
                    homeLatitude = state.homeLatitude,
                    homeLongitude = state.homeLongitude,
                    reminderTime = state.reminderTime
                )
                saveUserProfileUseCase(profile)
                _uiState.update { it.copy(isLoading = false, isComplete = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Gagal menyimpan profil: ${e.message}")
                }
            }
        }
    }
}
