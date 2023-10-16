package com.example.rssfeed.feature.rss_reader.list

import com.example.rss_domain.model.RssParseErrorData
import com.example.rss_domain_test.model.RssFeedItemDataGenerator
import com.example.rssfeed.feature.rss_reader.list.model.RssParserErrorDisplay
import com.example.rssfeed.feature.rss_reader.model.RssDisplayMapperUtils
import com.example.rssfeed.utils.MainCoroutineRule
import com.example.rssfeed.utils.assertStateFlowValues
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class RssListViewModelTest {

    private val subscribeToRssFeedFromUrlUseCase = MockSubscribeToRssFeedUseCase()

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

    @After
    fun clear() {
        subscribeToRssFeedFromUrlUseCase.clear()
    }

    @Test
    fun `WHEN viewModel is initialized THEN should post Loading state`() {
        assertThat(viewModel.state.value).isEqualTo(RssListState.Loading)
    }

    @Test
    fun `GIVEN that for given URL subscription returns ConnectionError error WHEN viewModel is initialized THEN should post Loading state`() =
        runTest {
            val url = "url"

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
                emitBlock = {
                    subscribeToRssFeedFromUrlUseCase.addValue(
                        Result.failure(RssParseErrorData.ConnectionError)
                    )
                },
                assertBlock = { states ->
                    assertThat(states[0]).isEqualTo(RssListState.Loading)

                    assertThat(states[1]).isEqualTo(
                        RssListState.Error(
                            connectionError = RssParserErrorDisplay.ConnectionError
                        )
                    )
                }
            )
        }

    @Test
    fun `GIVEN that for given URL subscription returns rss list WHEN getRssFeed is called THEN should post Data state`() =
        runTest {
            val url = "url"
            val domainModel = RssFeedItemDataGenerator.rssListItemData()

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
                emitBlock = {
                    subscribeToRssFeedFromUrlUseCase.addValue(
                        Result.success(listOf(domainModel))
                    )
                },
                assertBlock = { states ->
                    assertThat(states[0]).isEqualTo(RssListState.Loading)

                    assertThat(states[1]).isEqualTo(
                        RssListState.Data(
                            list = listOf(
                                RssDisplayMapperUtils.expectedListItemFromDomainModel(
                                    domainModel = domainModel,
                                    id = 0
                                )
                            )
                        )
                    )
                }
            )
        }

    @Test
    fun `GIVEN that for given URL subscription updates rss list WHEN viewModel is getRssFeed is called THEN should post Data state`() =
        runTest {
            val url = "url"
            val domainModel = RssFeedItemDataGenerator.rssListItemData()
            val secondDomainModel = RssFeedItemDataGenerator.rssListItemData(
                title = "second"
            )

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
                emitBlock = {
                    subscribeToRssFeedFromUrlUseCase.apply {
                        addValue(Result.success(listOf(domainModel)))

                        delay(500)

                        addValue(Result.success(listOf(domainModel, secondDomainModel)))
                    }
                },
                assertBlock = { states ->
                    assertThat(states[0]).isEqualTo(RssListState.Loading)

                    assertThat(states[1]).isEqualTo(
                        RssListState.Data(
                            list = listOf(
                                RssDisplayMapperUtils.expectedListItemFromDomainModel(
                                    domainModel = domainModel,
                                    id = 0
                                )
                            )
                        )
                    )

                    advanceUntilIdle()

                    assertThat(states[2]).isEqualTo(
                        RssListState.Data(
                            list = listOf(
                                RssDisplayMapperUtils.expectedListItemFromDomainModel(
                                    domainModel = domainModel,
                                    id = 0
                                ),
                                RssDisplayMapperUtils.expectedListItemFromDomainModel(
                                    domainModel = secondDomainModel,
                                    id = 1
                                )
                            )
                        )
                    )
                }
            )
        }

    @Test
    fun `GIVEN that for given URL subscription updates with error WHEN viewModel is getRssFeed is called THEN should post Error state`() =
        runTest {
            val url = "url"
            val domainModel = RssFeedItemDataGenerator.rssListItemData()

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
                emitBlock = {
                    subscribeToRssFeedFromUrlUseCase.apply {
                        addValue(Result.success(listOf(domainModel)))

                        delay(5_000)

                        addValue(Result.failure(RssParseErrorData.ConnectionError))
                    }
                },
                assertBlock = { states ->
                    assertThat(states[0]).isEqualTo(RssListState.Loading)

                    assertThat(states[1]).isEqualTo(
                        RssListState.Data(
                            list = listOf(
                                RssDisplayMapperUtils.expectedListItemFromDomainModel(
                                    domainModel = domainModel,
                                    id = 0
                                )
                            )
                        )
                    )

                    advanceUntilIdle()

                    assertThat(states[2]).isEqualTo(
                        RssListState.Error(
                            connectionError = RssParserErrorDisplay.ConnectionError
                        )
                    )
                }
            )
        }
}
