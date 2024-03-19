package com.example.kas.kas_note.presentation.kass

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.use_case.KasUseCases
import com.example.kas.kas_note.domain.util.OrderType
import com.example.kas.kas_note.domain.util.KasesOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KasesViewModel @Inject constructor(
    private val kasUseCases: KasUseCases
) : ViewModel() {

    private val _state = mutableStateOf(KasesState())
    val state: State<KasesState> = _state

    private var recentlyDeletedKas: Kas? = null

    private var getKasJob: Job? = null

    init {
        getKas(KasesOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: KasesEvent) {
        when (event) {
            is KasesEvent.Order -> {
                if (state.value.kasesOrder::class == event.kasOrder::class &&
                    state.value.kasesOrder.orderType == event.kasOrder.orderType
                ) {
                    return
                }
                getKas(event.kasOrder)
            }
            is KasesEvent.DeleteKas -> {
                viewModelScope.launch {
                    kasUseCases.deleteKas(event.kas)
                    recentlyDeletedKas = event.kas
                }
            }
            is KasesEvent.RestoreKas -> {
                viewModelScope.launch {
                    kasUseCases.addKas(recentlyDeletedKas ?: return@launch)
                    recentlyDeletedKas = null
                }
            }
            is KasesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getKas(kasesOrder: KasesOrder) {
        getKasJob?.cancel()
        getKasJob = kasUseCases.getKases(kasesOrder)
            .onEach { kases ->
                _state.value = state.value.copy(
                    kases = kases,
                    kasesOrder = kasesOrder
                )
            }
            .launchIn(viewModelScope)
    }
}