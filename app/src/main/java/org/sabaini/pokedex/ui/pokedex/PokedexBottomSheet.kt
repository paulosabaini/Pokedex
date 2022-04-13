package org.sabaini.pokedex.ui.pokedex

import androidx.compose.material.*
import androidx.compose.runtime.*
import java.lang.reflect.Modifier

@Composable
@ExperimentalMaterialApi
fun SearchBottomSheetLayout(sheetState: ModalBottomSheetState, modifier: Modifier = Modifier()) {
    var text by remember { mutableStateOf("Hello") }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Label") }
            )
        }
    ) {}
}

@Composable
@ExperimentalMaterialApi
fun GenFilterBottomSheetLayout(sheetState: ModalBottomSheetState, modifier: Modifier = Modifier()) {

}