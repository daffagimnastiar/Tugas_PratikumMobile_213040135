package com.example.kas.kas_note.presentation.util

sealed class Screen(val route: String) {
    object KasesScreen: Screen("kases_screen")
    object AddEditKasScreen: Screen("add_edit_kas_screen")
}
