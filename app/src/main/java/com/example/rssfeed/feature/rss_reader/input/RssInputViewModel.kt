package com.example.rssfeed.feature.rss_reader.input

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RssInputViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RssInputState.DEFAULT)
    val state: StateFlow<RssInputState> = _state

    fun onTextChange(text: String) {
        _state.value = RssInputState(
            urlInputText = text,
            readButtonEnabled = text.isNotBlank()
        )
    }
}
