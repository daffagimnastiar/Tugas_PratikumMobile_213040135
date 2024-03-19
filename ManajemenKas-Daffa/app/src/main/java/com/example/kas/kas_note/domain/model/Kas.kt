package com.example.kas.kas_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kas.ui.theme.*

@Entity
data class Kas(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val kasColor = listOf(BluePale, BlueLight, Violet, BluePrimary, BabyGold)
    }
}

class InvalidKasException(message: String): Exception(message)