package com.example.rssfeed.feature.rss_reader.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rssfeed.feature.rss_reader.list.model.RssListItemDisplay

@Composable
internal fun RssFeedList(list: List<RssListItemDisplay>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        items(items = list, key = { it.id }) { rssListItem ->
            RssFeedListItem(rssListItem)
        }
    }
}

@Composable
private fun RssFeedListItem(item: RssListItemDisplay) {
    Column {
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider()
    }
}
