package io.viewpoint.moviedatabase.api

import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before

abstract class ApiTest {
    protected val mockServer = MockWebServer()

    @Before
    fun loadWebServer() {
        mockServer.start()
        println("start webserver")
    }

    @After
    fun shutdownWebServer() {
        mockServer.close()
        println("shutdown webserver")
    }

    fun withMockResponse(mockResponse: MockResponse, action: suspend () -> Unit) {
        mockServer.enqueue(mockResponse)
        runBlocking {
            action()
        }
    }
}