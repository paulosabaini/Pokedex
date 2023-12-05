package org.sabaini.pokedex.util

import java.util.Locale

fun String.toTitleCase() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
