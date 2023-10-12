package com.example.rssfeed.feature.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rssfeed.R
import com.example.rssfeed.ui.theme.RssFeedTheme

@Composable
internal fun RssInputRoute(
    rssInputViewModel: RssInputViewModel = hiltViewModel(),
    navigateToRssList: (String) -> Unit
) {
    val rssInputState by rssInputViewModel.state.collectAsStateWithLifecycle()

    RssUrlInputScreen(
        rssInputState = rssInputState,
        onUrlTextChange = rssInputViewModel::onTextChange,
        onReadButtonClick = { navigateToRssList.invoke(rssInputState.urlInputText) }
    )
}

@Composable
private fun RssUrlInputScreen(
    rssInputState: RssInputState,
    onUrlTextChange: (String) -> Unit,
    onReadButtonClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .padding(innerPaddings)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = rssInputState.urlInputText,
                onValueChange = onUrlTextChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onReadButtonClick,
                enabled = rssInputState.readButtonEnabled
            ) {
                Text(stringResource(R.string.button_read_rss_url_action))
            }
        }
    }
}

@Composable
@Preview
private fun EmptyRrsInputScreenPreview() {
    RssFeedTheme {
        RssUrlInputScreen(
            rssInputState = RssInputState.DEFAULT,
            onUrlTextChange = {},
            onReadButtonClick = {}
        )
    }
}

@Composable
@Preview
private fun TextInputScreenPreview() {
    RssFeedTheme {
        RssUrlInputScreen(
            rssInputState = RssInputState(
                urlInputText = "input.com",
                readButtonEnabled = true
            ),
            onUrlTextChange = {},
            onReadButtonClick = {}
        )
    }
}
