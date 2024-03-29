package com.example.rssfeed.feature.rss_reader.input

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rssfeed.R
import com.example.rssfeed.ui.theme.RssFeedTheme
import com.example.rssfeed.ui.utils.Sizes
import com.example.rssfeed.ui.utils.Spacings

@Composable
internal fun RssInputRoute(
    rssInputViewModel: RssInputViewModel = hiltViewModel(),
    navigateToRssList: (String) -> Unit
) {
    val rssInputState by rssInputViewModel.state.collectAsStateWithLifecycle()

    RssFeedTheme {
        RssUrlInputScreen(
            rssInputState = rssInputState,
            onUrlTextChange = rssInputViewModel::onTextChange,
            onReadButtonClick = { navigateToRssList.invoke(rssInputState.urlInputText) }
        )
    }
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
                .fillMaxSize()
                .padding(horizontal = Spacings.large),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = rssInputState.urlInputText,
                singleLine = true,
                label = { Text(stringResource(R.string.hint_url_input)) },
                onValueChange = onUrlTextChange
            )

            Spacer(modifier = Modifier.height(Spacings.large))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Sizes.defaultButtonSize),
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
private fun EmptyRssInputScreenPreview() {
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

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun TextInputScreenDarkModelPreview() {
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
