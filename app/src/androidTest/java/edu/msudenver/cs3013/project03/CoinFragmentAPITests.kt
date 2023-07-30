package edu.msudenver.cs3013.project03

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
class CoinFragmentTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        if (this::mockWebServer.isInitialized) {
            mockWebServer.shutdown()
        }
    }
    private fun mockWebServerResponse() {
        val mockedResponse = """
            {
              "data": [
                {
                  "id": "1",
                  "name": "Bitcoin",
                  "symbol": "BTC",
                  "quote": {
                    "USD": {
                      "price": 45000.0
                    }
                  }
                }
              ]
            }
            """
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockedResponse)
        )
    }
    @Test
    fun testRecyclerViewIsDisplayed() {
        // Set up mock response for the web server
        mockWebServerResponse()

        // Launch the fragment
        launchFragmentInContainer<CoinFragment>(Bundle().apply {
            putString(CoinFragment.ARG_BASE_URL, mockWebServer.url("/").toString())
        })

        // Add your assertions here to check if the RecyclerView is displayed
        // For example: onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
    // ... Rest of your tests

}
