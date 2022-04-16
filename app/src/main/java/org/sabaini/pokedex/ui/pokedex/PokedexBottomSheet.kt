package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.Black
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.TWO
import org.sabaini.pokedex.util.Enums

@Composable
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
fun SearchBottomSheetLayout(
    sheetState: ModalBottomSheetState,
    onSearch: (String) -> Unit
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
            Icon(
                Icons.Filled.Search,
                stringResource(R.string.search_placeholder),
                tint = Color.Black
            )
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
@ExperimentalFoundationApi
fun GenFilterBottomSheetLayout(sheetState: ModalBottomSheetState) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            GenerationsOptions()
        },
        sheetShape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.dimen_of_10_dp),
            topEnd = dimensionResource(R.dimen.dimen_of_10_dp)
        )
    ) {}
}

@Composable
@ExperimentalFoundationApi
fun GenerationsOptions() {
    val generations = Enums.Generations.values().asList()

    Column {
        Text(
            text = stringResource(R.string.generation),
            textAlign = Center,
            fontSize = dimensionResource(R.dimen.dimen_of_16_sp).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.dimen_of_10_dp))
        )
        LazyVerticalGrid(
            cells = GridCells.Fixed(TWO),
            contentPadding = PaddingValues(dimensionResource(R.dimen.dimen_of_10_dp))
        ) {
            items(generations.size) { index ->
                GenerationCard(
                    name = generations[index].gen,
                    image = painterResource(generations[index].drawable)
                )
            }
        }
    }
}

@Composable
fun GenerationCard(name: String, image: Painter) {
    Card(
        elevation = dimensionResource(R.dimen.dimen_of_3_dp),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.dimen_of_10_dp))
            .clickable { }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = name, textAlign = Center)
            Image(
                painter = image,
                contentDescription = name,
                modifier = Modifier
                    .width(dimensionResource(R.dimen.dimen_of_160_dp))
                    .height(dimensionResource(R.dimen.dimen_of_80_dp))
                    .padding(dimensionResource(R.dimen.dimen_of_5_dp))
            )
        }

    }
}

@Preview
@Composable
@ExperimentalFoundationApi
fun GenerationsOptionsPreview() {
    GenerationsOptions()
}

@Preview
@Composable
@ExperimentalComposeUiApi
fun SearchTextFieldPreview() {
    SearchTextField {}
}