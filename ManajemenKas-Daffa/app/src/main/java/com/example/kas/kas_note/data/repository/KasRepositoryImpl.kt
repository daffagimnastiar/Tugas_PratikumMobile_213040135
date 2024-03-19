package com.example.kas.kas_note.data.repository

import com.example.kas.kas_note.data.data_source.KasDao
import com.example.kas.kas_note.domain.model.Kas
import com.example.kas.kas_note.domain.repository.KasRepository
import kotlinx.coroutines.flow.Flow

class KasRepositoryImpl(
    private val dao: KasDao
) : KasRepository {

    override fun getKas(): Flow<List<Kas>> {
        return dao.getKas()
    }

    override suspend fun getKasById(id: Int): Kas? {
        return dao.getKasById(id)
    }

    override suspend fun insertKas(note: Kas) {
        dao.insertKas(note)
    }

    override suspend fun deleteKas(note: Kas) {
        dao.deleteKas(note)
    }
}