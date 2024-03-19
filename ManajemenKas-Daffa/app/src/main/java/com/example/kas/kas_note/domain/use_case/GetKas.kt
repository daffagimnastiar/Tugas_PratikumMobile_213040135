package com.example.kas.kas_note.domain.use_case

import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.repository.KasRepository

class GetKas(
    private val repository: KasRepository
) {

    suspend operator fun invoke(id: Int): Kas? {
        return repository.getKasById(id)
    }
}