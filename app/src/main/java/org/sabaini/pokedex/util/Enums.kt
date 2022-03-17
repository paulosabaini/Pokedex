package org.sabaini.pokedex.util

import androidx.compose.ui.graphics.Color

object Enums {
    enum class StatType(val stat: String, val color: Color) {
        HP("HP", Color(0xFFFF0000)),
        ATTACK("Attack", Color(0xFFF08030)),
        DEFENSE("Defense", Color(0xFFF8D030)),
        SPECIAL_ATTACK("Sp. Atk", Color(0xFF6890F0)),
        SPECIAL_DEFENSE("Sp. Def", Color(0xFF78C850)),
        SPEED("Speed", Color(0xFFF85888)),
    }
}