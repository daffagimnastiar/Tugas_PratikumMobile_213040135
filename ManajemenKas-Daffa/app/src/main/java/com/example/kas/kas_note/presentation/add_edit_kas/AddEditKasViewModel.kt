package com.example.kas.kas_note.presentation.add_edit_kas

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kas.kas_note.domain.model.InvalidKasException
import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.use_case.KasUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditKasViewModel @Inject constructor(
    private val kasUseCases: KasUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _kasTitle = mutableStateOf(KasTextFieldState(
        hint = "Enter Kas Manajemen..."
    ))
    val kasTitle: State<KasTextFieldState> = _kasTitle

    private val _kasContent = mutableStateOf(KasTextFieldState(
        hint = "Description of Kas Manajemen"
    ))
    val kasContent: State<KasTextFieldState> = _kasContent

    private val _kasColor = mutableStateOf(Kas.kasColor.random().toArgb())
    val kasColor: State<Int> = _kasColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentKasId: Int? = null

    init {
        savedStateHandle.get<Int>("kasId")?.let { kasId ->
            if(kasId != -1) {
                viewModelScope.launch {
                    kasUseCases.getKas(kasId)?.also { kas ->
                        currentKasId = kas.id
                        _kasTitle.value = kasTitle.value.copy(
                            text = kas.title,
                            isHintVisible = false
                        )
                        _kasContent.value = _kasContent.value.copy(
                            text = kas.content,
                            isHintVisible = false
                        )
                        _kasColor.value = kas.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditKasEvent) {
        when(event) {
            is AddEditKasEvent.EnteredTitle -> {
                _kasTitle.value = kasTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditKasEvent.ChangeTitleFocus -> {
                _kasTitle.value = kasTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            kasTitle.value.text.isBlank()
                )
            }
            is AddEditKasEvent.EnteredContent -> {
                _kasContent.value = _kasContent.value.copy(
                    text = event.value
                )
            }
            is AddEditKasEvent.ChangeContentFocus -> {
                _kasContent.value = _kasContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _kasContent.value.text.isBlank()
                )
            }
            is AddEditKasEvent.ChangeColor -> {
                _kasColor.value = event.color
            }
            is AddEditKasEvent.SaveKas -> {
                viewModelScope.launch {
                    try {
                        kasUseCases.addKas(
                            Kas(
                                title = kasTitle.value.text,
                                content = kasContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = kasColor.value,
                                id = currentKasId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveKas)
                    } catch(e: InvalidKasException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save kas"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveKas: UiEvent()
    }
}