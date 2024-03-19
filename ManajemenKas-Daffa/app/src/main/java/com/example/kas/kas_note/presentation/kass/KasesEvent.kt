package com.example.kas.kas_note.presentation.kass

import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.util.KasesOrder

sealed class KasesEvent {
    data class Order(val kasOrder: KasesOrder): KasesEvent()
    data class DeleteKas(val kas: Kas): KasesEvent()
    object RestoreKas: KasesEvent()
    object ToggleOrderSection: KasesEvent()
}
