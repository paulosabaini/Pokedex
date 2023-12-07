package org.sabaini.pokedex.presentation.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.sabaini.pokedex.presentation.theme.Black
import org.sabaini.pokedex.util.Constants.BLANK
import org.sabaini.pokedex.util.Constants.TWO
import org.sabaini.pokedex.util.Enums

@Composable
@ExperimentalComposeUiApi
fun SearchBottomSheetLayout(
    onDismiss: () -> Unit,
    onSearch: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Black,
        shape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.dimen_of_10_dp),
            topEnd = dimensionResource(R.dimen.dimen_of_10_dp),
        ),
    ) {
        SearchTextField(onSearch = onSearch)
    }
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
            },
        ),
        placeholder = {
            Text(stringResource(R.string.search_placeholder))
        },
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_of_10_dp)),
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                stringResource(R.string.search_placeholder),
                tint = Color.Black,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_16_dp)),
    )
}

@Composable
@ExperimentalFoundationApi
fun GenFilterBottomSheetLayout(onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color.White,
        shape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.dimen_of_10_dp),
            topEnd = dimensionResource(R.dimen.dimen_of_10_dp),
        ),
    ) {
        GenerationsOptions()
    }
}

@Composable
@ExperimentalFoundationApi
fun GenerationsOptions() {
    val generations = Enums.Generations.entries

    Column {
        Text(
            text = stringResource(R.string.generation),
            textAlign = Center,
            fontSize = dimensionResource(R.dimen.dimen_of_16_sp).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.dimen_of_10_dp)),
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(TWO),
            contentPadding = PaddingValues(dimensionResource(R.dimen.dimen_of_10_dp)),
        ) {
            items(generations) { generation ->
                GenerationCard(
                    name = generation.gen,
                    image = painterResource(generation.drawable),
                )
            }
        }
    }
}

@Composable
fun GenerationCard(name: String, image: Painter) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.dimen_of_3_dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.dimen_of_10_dp))
            .clickable { },
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = name, textAlign = Center)
            Image(
                painter = image,
                contentDescription = name,
                modifier = Modifier
                    .width(dimensionResource(R.dimen.dimen_of_160_dp))
                    .height(dimensionResource(R.dimen.dimen_of_80_dp))
                    .padding(dimensionResource(R.dimen.dimen_of_5_dp)),
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
