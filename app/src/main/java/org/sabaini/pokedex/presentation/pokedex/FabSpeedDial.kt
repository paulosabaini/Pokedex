package org.sabaini.pokedex.presentation.pokedex

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import org.sabaini.pokedex.R
import org.sabaini.pokedex.presentation.theme.Red
import org.sabaini.pokedex.presentation.theme.White

@Composable
fun FabSpeedDial(onClick: (Int) -> Unit) {
    AndroidView(factory = { context ->
        SpeedDialView(context).apply {
            mainFabClosedBackgroundColor = Red.toArgb()
            mainFabOpenedBackgroundColor = Red.toArgb()
            mainFabClosedIconColor = White.toArgb()
            mainFabOpenedIconColor = White.toArgb()
            setMainFabClosedDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.baseline_tune_24,
                ),
            )
            setMainFabOpenedDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.outline_close_24,
                ),
            )

            addActionItem(
                SpeedDialActionItem.Builder(
                    R.id.menu_item_gen,
                    R.drawable.baseline_catching_pokemon_24,
                )
                    .setLabel(R.string.all_gen)
                    .setFabBackgroundColor(White.toArgb())
                    .create(),
            )
            addActionItem(
                SpeedDialActionItem.Builder(
                    R.id.menu_item_search,
                    R.drawable.baseline_search_24,
                )
                    .setLabel(R.string.search)
                    .setFabBackgroundColor(White.toArgb())
                    .create(),
            )

            setOnActionSelectedListener { actionItem ->
                onClick(actionItem.id)
                this.close()
                return@setOnActionSelectedListener true
            }
        }
    })
}
