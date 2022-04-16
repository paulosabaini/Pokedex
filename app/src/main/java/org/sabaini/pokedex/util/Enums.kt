package org.sabaini.pokedex.util

import androidx.compose.ui.graphics.Color
import org.sabaini.pokedex.R

object Enums {
    enum class StatType(val stat: String, val color: Color) {
        HP("HP", Color(0xFFFF0000)),
        ATTACK("Attack", Color(0xFFF08030)),
        DEFENSE("Defense", Color(0xFFF8D030)),
        SPECIAL_ATTACK("Sp. Atk", Color(0xFF6890F0)),
        SPECIAL_DEFENSE("Sp. Def", Color(0xFF78C850)),
        SPEED("Speed", Color(0xFFF85888)),
    }

    enum class PokemonTypeColor(val color: Color) {
        NORMAL(Color(0xffa9a87b)),
        FIRE(Color(0xfff8803d)),
        FIGHTING(Color(0xffc8312e)),
        WATER(Color(0xff6090ec)),
        FLYING(Color(0xffa991ed)),
        GRASS(Color(0xff6dc859)),
        POISON(Color(0xffa6419e)),
        ELECTRIC(Color(0xfffdd047)),
        GROUND(Color(0xffe4c070)),
        PSYCHIC(Color(0xffff5989)),
        ROCK(Color(0xffbba043)),
        ICE(Color(0xff8fd8d7)),
        BUG(Color(0xffa7b836)),
        DRAGON(Color(0xff713bf3)),
        GHOST(Color(0xff715896)),
        DARK(Color(0xff725849)),
        STEEL(Color(0xffb8b8cf)),
        FAIRY(Color(0xfff599ac)),
        UNDEFINED(Color(0xff60a090)),
    }

    enum class Generations(val gen: String, val drawable: Int) {
        GEN_1("Generation I", R.drawable.gen_1),
        GEN_2("Generation II", R.drawable.gen_2),
        GEN_3("Generation III", R.drawable.gen_3),
        GEN_4("Generation IV", R.drawable.gen_4),
        GEN_5("Generation V", R.drawable.gen_5),
        GEN_6("Generation VI", R.drawable.gen_6),
        GEN_7("Generation VII", R.drawable.gen_7),
        GEN_8("Generation VIII", R.drawable.gen_8),
    }
}