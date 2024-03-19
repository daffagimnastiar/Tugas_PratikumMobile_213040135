package com.example.kas.kas_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kas.kas_note.domain.model.Kas

@Database(
    entities = [Kas::class],
    version = 3
)
abstract class KasDatabase: RoomDatabase() {

    abstract val kasDao: KasDao

    companion object {
        const val DATABASE_NAME = "kas_db"
    }
}