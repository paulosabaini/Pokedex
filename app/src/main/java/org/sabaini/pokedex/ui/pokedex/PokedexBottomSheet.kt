package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun GenerationsOptions() {
    Column {
        Text(
            text = "Generation",
            textAlign = Center,
            fontSize = dimensionResource(R.dimen.dimen_of_16_sp).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.dimen_of_10_dp))
        )
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp))) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Generation(name = "Generation I", image = painterResource(id = R.drawable.gen_1))
                Generation(name = "Generation II", image = painterResource(id = R.drawable.gen_2))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Generation(name = "Generation III", image = painterResource(id = R.drawable.gen_3))
                Generation(name = "Generation IV", image = painterResource(id = R.drawable.gen_4))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Generation(name = "Generation V", image = painterResource(id = R.drawable.gen_5))
                Generation(name = "Generation VI", image = painterResource(id = R.drawable.gen_6))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Generation(name = "Generation VII", image = painterResource(id = R.drawable.gen_7))
                Generation(name = "Generation VIII", image = painterResource(id = R.drawable.gen_8))
            }
        }
    }
}

@Composable
fun Generation(name: String, image: Painter) {
    Card(
        elevation = dimensionResource(R.dimen.dimen_of_3_dp),
        modifier = Modifier
            .padding(
                bottom = dimensionResource(
                    R.dimen.dimen_of_10_dp
                )
            )
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
fun GenerationsOptionsPreview() {
    GenerationsOptions()
}