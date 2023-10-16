package com.example.rss_domain

import com.example.rss_domain.mock.RssItemResponseGenerator
import com.example.rss_domain.model.RssFeedItemData
import com.example.rss_domain.model.RssFeedItemDataMapperValidator
import com.example.rss_domain.model.RssParseErrorData
import com.example.rss_repository.RssRepository
import com.example.rss_repository.model.RssParserErrorResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultSubscribeToRssFeedFromUrlUseCaseTest {

    private val rssRepository = mockk<RssRepository>()

    private lateinit var useCase: SubscribeToRssFeedFromUrlUseCase

    @Before
    fun setup() {
        useCase = DefaultSubscribeToRssFeedFromUrlUseCase(rssRepository)
    }

    @Test
    fun `GIVEN that repository returns list of items THEN should post mapped results`() = runTest {
        val url = "url"
        val responseModel = RssItemResponseGenerator.rssItemResponse()
        coEvery { rssRepository.parseRssUrl(url) } returns listOf(responseModel)

        val results = useCase.execute(url).first()

        assertThat(results)
            .isEqualTo(
                Result.success(
                    listOf(
                        RssFeedItemDataMapperValidator.expectedMapFromResponseModel(responseModel)
                    )
                )
            )
    }

    @Test
    fun `GIVEN that repository throws ParsingError THEN should return domain model of ParsingError`() =
        runTest {
            val url = "url"
            coEvery { rssRepository.parseRssUrl(url) } throws RssParserErrorResponse.ParsingError

            val results = useCase.execute(url).first()

            assertThat(results).isEqualTo(
                Result.failure<List<RssFeedItemData>>(RssParseErrorData.ParsingError)
            )
        }

    @Test
    fun `GIVEN that repository throws error WHEN repository returns value after the delay THEN should post flow containing recovered state`() =
        runTest {
            val url = "url"
            val responseModel = RssItemResponseGenerator.rssItemResponse()
            coEvery { rssRepository.parseRssUrl(url) } throws RssParserErrorResponse.ConnectionError

            val values = mutableListOf<Result<List<RssFeedItemData>>>()

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                useCase.execute(url).toList(values)
            }

            coEvery { rssRepository.parseRssUrl(url) } returns listOf(responseModel)
            advanceTimeBy(10_001)
            coEvery { rssRepository.parseRssUrl(url) } throws RssParserErrorResponse.ParsingError
            advanceTimeBy(10_001)

            assertThat(values).isEqualTo(
                listOf(
                    Result.failure(RssParseErrorData.ConnectionError),
                    Result.success(
                        listOf(
                            RssFeedItemDataMapperValidator.expectedMapFromResponseModel(
                                responseModel
                            )
                        )
                    ),
                    Result.failure(RssParseErrorData.ParsingError)
                )
            )
        }
}
