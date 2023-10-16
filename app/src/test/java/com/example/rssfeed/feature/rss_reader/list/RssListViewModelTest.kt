package com.example.rssfeed.feature.rss_reader.list

import com.example.rss_domain.SubscribeToRssFeedFromUrlUseCase
import com.example.rss_domain.model.RssParseErrorData
import com.example.rss_domain_test.model.RssFeedItemDataGenerator
import com.example.rssfeed.feature.rss_reader.list.model.RssParserErrorDisplay
import com.example.rssfeed.feature.rss_reader.model.RssDisplayMapperUtils
import com.example.rssfeed.utils.MainCoroutineRule
import com.example.rssfeed.utils.assertStateFlowValues
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
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
        assertThat(viewModel.state.value).isEqualTo(RssListState.Loading)
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
            every { subscribeToRssFeedFromUrlUseCase.execute(url) } returns flowOf(
                listOf(
                    domainModel
                )
            )

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
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

            every { subscribeToRssFeedFromUrlUseCase.execute(url) } returns flow {
                emit(listOf(domainModel))

                delay(5000)

                emit(listOf(domainModel, secondDomainModel))
            }

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
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

            every { subscribeToRssFeedFromUrlUseCase.execute(url) } returns flow {
                emit(listOf(domainModel))

                delay(5000)

                throw RssParseErrorData.ConnectionError
            }

            assertStateFlowValues(
                stateFlow = viewModel.state,
                checkBlock = { viewModel.getRssFeed(url) },
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
