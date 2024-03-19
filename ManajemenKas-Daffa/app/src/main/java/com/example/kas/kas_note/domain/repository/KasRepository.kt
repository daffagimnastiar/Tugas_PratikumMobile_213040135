package com.example.kas.kas_note.domain.repository

import com.example.kas.kas_note.domain.model.Kas
import kotlinx.coroutines.flow.Flow

interface KasRepository {

    fun getKas(): Flow<List<Kas>>

    suspend fun getKasById(id: Int): Kas?

    suspend fun insertKas(note: Kas)

    suspend fun deleteKas(note: Kas)
}