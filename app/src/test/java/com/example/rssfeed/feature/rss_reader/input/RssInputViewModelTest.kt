package com.example.rssfeed.feature.rss_reader.input

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class RssInputViewModelTest {

    private lateinit var viewModel: RssInputViewModel

    @Before
    fun setup() {
        viewModel = RssInputViewModel()
    }

    @Test
    fun `WHEN ViewModel is initialzed THEN should post valid default state`() {
        assertThat(viewModel.state.value).isEqualTo(
            RssInputState(
                urlInputText = "",
                readButtonEnabled = false
            )
        )
    }

    @Test
    fun `WHEN text is changed to empty text THEN should post disabled state`() {
        viewModel.onTextChange(" ")

        assertThat(viewModel.state.value).isEqualTo(
            RssInputState(
                urlInputText = " ",
                readButtonEnabled = false
            )
        )
    }

    @Test
    fun `WHEN text is changed to non-empty text THEN should post enabled state`() {
        viewModel.onTextChange("test")

        assertThat(viewModel.state.value).isEqualTo(
            RssInputState(
                urlInputText = "test",
                readButtonEnabled = true
            )
        )
    }
}
