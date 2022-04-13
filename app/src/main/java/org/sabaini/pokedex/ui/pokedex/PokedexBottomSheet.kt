package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.ui.theme.Red
import org.sabaini.pokedex.util.Constants.BLANK

@Composable
@ExperimentalMaterialApi
fun SearchBottomSheetLayout(sheetState: ModalBottomSheetState, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(BLANK) }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                              Text(stringResource(R.string.search_placeholder))
                },
                shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_of_10_dp)),
                leadingIcon = {
                    Icon(Icons.Filled.Search, BLANK, tint = Color.Black)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.dimen_of_16_dp))
            )
        },
        sheetBackgroundColor = Black,
        sheetShape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.dimen_of_10_dp),
            topEnd = dimensionResource(R.dimen.dimen_of_10_dp)
        )
) {}
}

@Composable
@ExperimentalMaterialApi
fun GenFilterBottomSheetLayout(sheetState: ModalBottomSheetState, modifier: Modifier = Modifier) {

}