package edu.msudenver.cs3013.project03

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteFragmentTest {

    @Before
    fun setup() {
        // Launch the fragment in a test environment.
        val scenario = launchFragmentInContainer<FavoriteFragment>()

        // Mock data
        val mockData = Cryptocurrency("1", "Bitcoin", "BTC", Quote(Price(45000.0)))
//        val mockNftData = NftItem() // Assuming you have a constructor or other method to create NftItem

        // Provide ViewModel to fragment with mock data
        scenario.onFragment { fragment ->
            val viewModel = ViewModelProvider(fragment).get(FavoritesViewModel::class.java)
            viewModel.addToFavorites(mockData)
//            viewModel.addToNftFavorites(mockNftData)
        }
    }

    @Test
    fun testCryptocurrencyIsDisplayed() {
        onView(withText("Bitcoin")).check(matches(isDisplayed()))
    }

    @Test
    fun testSwipeToRemoveCryptocurrency() {
        onView(withText("Bitcoin")).perform(swipeLeft())
        onView(withText("Bitcoin")).check(matches(not(isDisplayed())))
    }
    @Test
    fun testFavoriteFragmentLoaded() {
        val uri = Uri.parse("http://msudenver.edu/favoriteFragment")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        ActivityScenario.launch<MainActivity>(intent)

        // Now the FavoriteFragment should be loaded. Perform your tests here.
        onView(withId(R.id.recyclerViewFavorites))
            .check(matches(isDisplayed()))
    }


    // Add similar tests for Nft items

    // Note: This is a basic setup. You might need to adjust based on your exact requirements and app setup.
}
