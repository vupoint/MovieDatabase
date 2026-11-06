package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.api.util.MockResponseReader
import junit.framework.Assert.fail
import mockwebserver3.MockResponse
import org.junit.Test
import retrofit2.HttpException
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

class SearchApiTest : ApiTest() {
    private val api by lazy {
        MovieDatabaseApi.Builder()
            .apply {
                url = mockServer.url("/").toString()
                apiKey = "test"
            }.build()
    }

    @Test
    fun `success to search movie`() =
        withMockResponse(
            MockResponse.Builder()
                .body(MockResponseReader.fromFile("responses/search/spider-man.json"))
                .build()
        ) {
            val api = api.get<SearchApi>()

            val response = api.searchMovie("")
            expectThat(response) {
                get { page }.isEqualTo(1)
                get { total_results }.isEqualTo(56)
            }
        }

    @Test
    fun `fail to search movie because of authorization`() =
        withMockResponse(
            MockResponse.Builder()
                .code(401)
                .body(MockResponseReader.fromFile("responses/search/spider-man.json"))
                .build()
        ) {
            val api = api.get<SearchApi>()
            val result = runCatching {
                api.searchMovie("")
            }

            expectThat(result.isFailure).isTrue()
            result
                .onSuccess {
                    fail("response must have a error")
                }
                .onFailure { exception ->
                    if (exception is HttpException) {
                        expectThat(exception.code()).isEqualTo(401)
                    } else {
                        fail("error must be HttpException")
                    }
                }
        }
}