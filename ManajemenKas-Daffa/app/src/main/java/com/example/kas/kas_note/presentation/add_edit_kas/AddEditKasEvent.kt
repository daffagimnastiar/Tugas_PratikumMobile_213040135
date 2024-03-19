package com.example.kas.kas_note.presentation.add_edit_kas

import androidx.compose.ui.focus.FocusState

sealed class AddEditKasEvent{
    data class EnteredTitle(val value: String): AddEditKasEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditKasEvent()
    data class EnteredContent(val value: String): AddEditKasEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditKasEvent()
    data class ChangeColor(val color: Int): AddEditKasEvent()
    object SaveKas: AddEditKasEvent()
}

