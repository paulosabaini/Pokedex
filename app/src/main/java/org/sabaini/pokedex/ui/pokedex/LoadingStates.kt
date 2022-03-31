package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.sabaini.pokedex.R
import org.sabaini.pokedex.ui.theme.PokedexTheme

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun PreviewLoadingView() {
    PokedexTheme {
        LoadingView()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_of_16_dp))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Preview
@Composable
fun PreviewLoadingItem() {
    PokedexTheme {
        LoadingItem()
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.dimen_of_10_dp))
        )
        Button(
            onClick = onClickRetry,
            contentPadding = PaddingValues(dimensionResource(R.dimen.dimen_of_10_dp))
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
fun PreviewErrorItem() {
    PokedexTheme {
        ErrorItem(
            message = "Error message",
            onClickRetry = {}
        )
    }
}