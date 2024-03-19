package com.example.kas.kas_note.presentation.kass

import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.util.KasesOrder
import com.example.kas.kas_note.domain.util.OrderType

data class KasesState(
    val kases: List<Kas> = emptyList(),
    val kasesOrder: KasesOrder = KasesOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
