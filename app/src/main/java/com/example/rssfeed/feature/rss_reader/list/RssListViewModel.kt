package com.example.rssfeed.feature.rss_reader.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rss_domain.GetRssFeedFromUrlUseCase
import com.example.rss_domain.model.RssFeedItemData
import com.example.rss_domain.model.RssParseErrorData
import com.example.rssfeed.feature.rss_reader.list.model.RssParserErrorDisplay
import com.example.rssfeed.feature.rss_reader.list.model.toDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RssListViewModel @Inject constructor(
    private val getRssFeedFromUrlUseCase: GetRssFeedFromUrlUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RssListState>(RssListState.Loading)
    val state: StateFlow<RssListState> = _state

    private val _feedList = MutableSharedFlow<List<RssFeedItemData>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            _feedList.collect { feedList ->
                _state.value = RssListState.Data(
                    list = feedList.mapIndexed { index, feedItem -> feedItem.toDisplay(index) }
                )
            }
        }
    }

    fun getRssFeed(url: String) {
        viewModelScope.launch {
            _state.value = RssListState.Loading

            getRssFeedFromUrlUseCase.execute(url).fold(
                onSuccess = {
                    _feedList.emit(it)
                },
                onFailure = { error ->
                    val displayError = (error as? RssParseErrorData)?.toDisplay()
                        ?: RssParserErrorDisplay.UnknownError

                    _state.value = RssListState.Error(displayError)
                }
            )
        }
    }
}
