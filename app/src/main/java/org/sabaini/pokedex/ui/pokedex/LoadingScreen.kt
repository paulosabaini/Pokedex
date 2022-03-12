package org.sabaini.pokedex.ui.pokedex

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .padding(16.dp)
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
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
        Button(onClick = onClickRetry, contentPadding = PaddingValues(10.dp)) {
            Text(text = "Retry")
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