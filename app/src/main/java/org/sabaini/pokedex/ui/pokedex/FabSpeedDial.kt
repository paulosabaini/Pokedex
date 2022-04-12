package org.sabaini.pokedex.ui.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Red
import org.sabaini.pokedex.ui.theme.RedVariant

@Composable
fun FabSpeedDial(onClick: () -> Unit) {
    AndroidView(factory = { context ->
        SpeedDialView(context).apply {
            mainFabClosedBackgroundColor = Red.toArgb()
            mainFabOpenedBackgroundColor = RedVariant.toArgb()
            addActionItem(
                SpeedDialActionItem.Builder(
                    R.id.auto,
                    R.drawable.baseline_search_24
                ).create()
            )
            addActionItem(
                SpeedDialActionItem.Builder(
                    R.id.auto,
                    R.drawable.baseline_catching_pokemon_24
                ).create()
            )
        }
    })
}