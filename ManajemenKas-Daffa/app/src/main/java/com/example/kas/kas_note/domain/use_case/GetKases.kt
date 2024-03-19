package com.example.kas.kas_note.domain.use_case

import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.repository.KasRepository
import com.example.kas.kas_note.domain.util.KasesOrder
import com.example.kas.kas_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetKases(
    private val repository: KasRepository
) {

    operator fun invoke(
        recipesOrder: KasesOrder = KasesOrder.Date(OrderType.Descending)
    ): Flow<List<Kas>> {
        return repository.getKas().map { recipess ->
            when(recipesOrder.orderType) {
                is OrderType.Ascending -> {
                    when(recipesOrder) {
                        is KasesOrder.Title -> recipess.sortedBy { it.title.lowercase() }
                        is KasesOrder.Date -> recipess.sortedBy { it.timestamp }
                        is KasesOrder.Color -> recipess.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(recipesOrder) {
                        is KasesOrder.Title -> recipess.sortedByDescending { it.title.lowercase() }
                        is KasesOrder.Date -> recipess.sortedByDescending { it.timestamp }
                        is KasesOrder.Color -> recipess.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}