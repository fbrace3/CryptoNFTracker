package edu.msudenver.cs3013.project03

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoinFragmentTest {

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setup() {
        // 1. Start the MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // 2. Override the base URL for Retrofit
        // For this, you will use the newInstance function of CoinFragment to pass the mock server's URL
        val coinFragment = CoinFragment.newInstance(mockWebServer.url("/").toString())

        // 3. Set up the fragment for testing
        // Assuming you're using FragmentScenario for testing, you'd launch the fragment like this:
        val scenario = launchFragmentInContainer {
            coinFragment
        }

        // If you have any additional setup requirements like setting a test ViewModel, you can do it here.
    }


    @Test
    fun testSuccessfulApiCall() {
        // Enqueue a mock response for the expected API call
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("...")) // replace ... with your actual JSON response

        // Launch the fragment
        launchFragmentInContainer<CoinFragment>()

        // Check if RecyclerView is displayed after data is fetched
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        // You can add further tests, like checking if a specific item is displayed,
        // or performing actions on the items, and so on.
    }

    @Test
    fun testApiCallFailure() {
        // Enqueue a failure response
        mockWebServer.enqueue(MockResponse().setResponseCode(400))

        // Launch the fragment
        launchFragmentInContainer<CoinFragment>()

        // Verify appropriate error behavior - For instance, you might display an error message
        // onView(withId(R.id.error_message)).check(matches(isDisplayed()))
    }

    // Add more tests based on different API responses or UI interactions

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
