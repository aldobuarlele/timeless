package com.rivaldo.timeless.presentation.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.timeless.domain.usecase.GetLogForDateUseCase
import com.rivaldo.timeless.domain.usecase.SaveDailyLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * UI State for the Diary screen.
 *
 * @property text The current text input by the user.
 * @property selectedPhotoUris List of content URI strings for selected photos.
 * @property isSaving Whether the save operation is in progress.
 * @property error An error message to display, if any.
 * @property savedSuccessfully Indicates the log was saved and the screen can close.
 */
data class DiaryUiState(
    val text: String = "",
    val selectedPhotoUris: List<String> = emptyList(),
    val isSaving: Boolean = false,
    val error: String? = null,
    val savedSuccessfully: Boolean = false
) {
    /**
     * The "Simpan" button is only enabled if:
     * - Text is at least 50 characters (minimum 1 sentence)
     * - Not currently saving
     */
    val isSaveEnabled: Boolean
        get() = text.trim().length >= SaveDailyLogUseCase.MIN_CONTENT_LENGTH && !isSaving
}

/**
 * ViewModel for the Diary entry screen.
 *
 * Manages text input, photo selection, and the save operation.
 * Validates minimum content length (1 sentence) before enabling the save button.
 */
@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val saveDailyLogUseCase: SaveDailyLogUseCase,
    private val getLogForDateUseCase: GetLogForDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiaryUiState())
    val uiState: StateFlow<DiaryUiState> = _uiState.asStateFlow()

    /**
     * Update the journal text as the user types.
     */
    fun onTextChanged(text: String) {
        _uiState.update { it.copy(text = text, error = null) }
    }

    /**
     * Add a photo URI to the selected photos list.
     * Called when the user picks a photo from the gallery.
     */
    fun onPhotoSelected(uriString: String) {
        _uiState.update {
            val updatedList = it.selectedPhotoUris + uriString
            it.copy(selectedPhotoUris = updatedList)
        }
    }

    /**
     * Remove a photo from the selected list by its URI.
     */
    fun onPhotoRemoved(uriString: String) {
        _uiState.update {
            it.copy(
                selectedPhotoUris = it.selectedPhotoUris.filter { s -> s != uriString }
            )
        }
    }

    /**
     * Save the daily log entry.
     * Validates content, processes photos (EXIF + micro-thumbnails), and persists to Room.
     */
    fun onSaveClicked() {
        val currentState = _uiState.value
        if (!currentState.isSaveEnabled) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }

            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

            val result = saveDailyLogUseCase(
                date = today,
                content = currentState.text,
                photoUris = currentState.selectedPhotoUris
            )

            when (result) {
                is SaveDailyLogUseCase.SaveResult.ContentTooShort -> {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            error = "Tulislah minimal satu kalimat (${SaveDailyLogUseCase.MIN_CONTENT_LENGTH} karakter) untuk menyimpan jurnal."
                        )
                    }
                }
                is SaveDailyLogUseCase.SaveResult.Success -> {
                    _uiState.update {
                        it.copy(isSaving = false, savedSuccessfully = true)
                    }
                }
            }
        }
    }

    /**
     * Reset the saved state flag after navigation has been handled.
     */
    fun onNavigatedAway() {
        _uiState.update { it.copy(savedSuccessfully = false) }
    }
}
