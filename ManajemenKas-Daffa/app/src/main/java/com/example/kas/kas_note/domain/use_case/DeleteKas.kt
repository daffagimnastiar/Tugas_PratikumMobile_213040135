package com.example.kas.kas_note.domain.use_case

import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.repository.KasRepository

class DeleteKas(
    private val repository: KasRepository
) {

    suspend operator fun invoke(kas: Kas) {
        repository.deleteKas(kas)
    }
}