package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.util.Constants.BLANK

@Composable
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
fun SearchBottomSheetLayout(
    sheetState: ModalBottomSheetState,
    onSearch: (String) -> kotlin.Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SearchTextField(onSearch = onSearch)
        },
        sheetBackgroundColor = Black,
        sheetShape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.dimen_of_10_dp),
            topEnd = dimensionResource(R.dimen.dimen_of_10_dp)
        )
    ) {}
}

@Composable
@ExperimentalComposeUiApi
fun SearchTextField(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf(BLANK) }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onSearch(text)
            }
        ),
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_16_dp))
    )
}

@Composable
@ExperimentalMaterialApi
fun GenFilterBottomSheetLayout(sheetState: ModalBottomSheetState, modifier: Modifier = Modifier) {

}