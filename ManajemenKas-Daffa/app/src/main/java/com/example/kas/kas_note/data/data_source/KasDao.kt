package com.example.kas.kas_note.data.data_source

import androidx.room.*
import com.example.kas.kas_note.domain.model.Kas
import kotlinx.coroutines.flow.Flow

@Dao
interface KasDao {

    @Query("SELECT * FROM kas")
    fun getKas(): Flow<List<Kas>>

    @Query("SELECT * FROM kas WHERE id = :id")
    suspend fun getKasById(id: Int): Kas?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKas(note: Kas)

    @Delete
    suspend fun deleteKas(note: Kas)
}