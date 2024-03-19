package com.example.kas.kas_note.domain.use_case

import com.example.kas.kas_note.domain.model.InvalidKasException
import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.repository.KasRepository

class AddKas(
    private val repository: KasRepository
) {

    @Throws(InvalidKasException::class)
    suspend operator fun invoke(kas: Kas) {
        if(kas.title.isBlank()) {
            throw InvalidKasException("The title of the kas can't be empty.")
        }
        if(kas.content.isBlank()) {
            throw InvalidKasException("The content of the kas can't be empty.")
        }
        repository.insertKas(kas)
    }
}