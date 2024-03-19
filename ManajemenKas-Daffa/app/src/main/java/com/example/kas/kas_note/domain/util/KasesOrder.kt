package com.example.kas.kas_note.domain.util

sealed class KasesOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): KasesOrder(orderType)
    class Date(orderType: OrderType): KasesOrder(orderType)
    class Color(orderType: OrderType): KasesOrder(orderType)

    fun copy(orderType: OrderType): KasesOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
