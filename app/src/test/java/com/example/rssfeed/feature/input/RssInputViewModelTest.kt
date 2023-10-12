package com.example.rssfeed.feature.input

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
        val expectedState = RssInputState(urlInputText = "", readButtonEnabled = false)

        val viewModelState = viewModel.state.value

        assert(expectedState == viewModelState)
    }

    @Test
    fun `WHEN text is changed to empty text THEN should post disabled state`() {
        viewModel.onTextChange(" ")

        val expectedState = RssInputState(urlInputText = " ", readButtonEnabled = false)

        assert(expectedState == viewModel.state.value)
    }

    @Test
    fun `WHEN text is changed to non-empty text THEN should post enabled state`() {
        viewModel.onTextChange("test")

        val expectedState = RssInputState(urlInputText = "test", readButtonEnabled = true)

        assert(expectedState == viewModel.state.value)
    }
}
