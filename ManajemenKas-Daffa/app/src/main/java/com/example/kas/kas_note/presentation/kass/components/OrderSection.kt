package com.example.kas.kas_note.presentation.kass.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kas.kas_note.domain.util.OrderType
import com.example.kas.kas_note.domain.util.KasesOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    kasOrder: KasesOrder = KasesOrder.Date(OrderType.Descending),
    onOrderChange: (KasesOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = kasOrder is KasesOrder.Title,
                onSelect = { onOrderChange(KasesOrder.Title(kasOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = kasOrder is KasesOrder.Date,
                onSelect = { onOrderChange(KasesOrder.Date(kasOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = kasOrder is KasesOrder.Color,
                onSelect = { onOrderChange(KasesOrder.Color(kasOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = kasOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(kasOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = kasOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(kasOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}