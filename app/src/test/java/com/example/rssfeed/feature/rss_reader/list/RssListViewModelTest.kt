package com.example.rssfeed.feature.rss_reader.list

import com.example.rss_domain.SubscribeToRssFeedFromUrlUseCase
import com.example.rss_domain.model.RssParseErrorData
import com.example.rssfeed.feature.rss_reader.list.model.RssParserErrorDisplay
import com.example.rssfeed.utils.MainCoroutineRule
import com.example.rssfeed.utils.assertStateFlowValues
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class RssListViewModelTest {

    private val subscribeToRssFeedFromUrlUseCase = mockk<SubscribeToRssFeedFromUrlUseCase>()

    private lateinit var viewModel: RssListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = RssListViewModel(
            subscribeToRssFeedFromUrlUseCase = subscribeToRssFeedFromUrlUseCase
        )
    }

    @Test
    fun `WHEN viewModel is initialized THEN should post Loading state`() {
        assert(viewModel.state.value == RssListState.Loading)
    }

    @Test
    fun `GIVEN that for given URL subscription returns ConnectionError error WHEN viewModel is initialized THEN should post Loading state`() =
        runTest {
            val url = "url"
            every { subscribeToRssFeedFromUrlUseCase.execute(url) } returns flow {
                throw RssParseErrorData.ConnectionError
            }

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
                assertBlock = { states ->
                    assert(states[0] == RssListState.Loading)

                    assert(
                        states[1] == RssListState.Error(
                            connectionError = RssParserErrorDisplay.ConnectionError
                        )
                    )
                }
            )
        }

    @Test
    fun `GIVEN that for given URL subscription returns rss list WHEN viewModel is initialized THEN should post Data state`() =
        runTest {
            val url = "url"
            every { subscribeToRssFeedFromUrlUseCase.execute(url) } returns flowOf()

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
                assertBlock = { states ->
                    assert(states[0] == RssListState.Loading)

                    assert(
                        states[1] == RssListState.Error(
                            connectionError = RssParserErrorDisplay.ConnectionError
                        )
                    )
                }
            )
        }
}
