package edu.msudenver.cs3013.project03

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteFragmentTest {
    @Before
    fun setup() {
        // Assuming FavoritesViewModel is filled with some default data
        // If there's any setup needed for the FavoritesViewModel, it should be done here
        launchFragmentInContainer<FavoriteFragment>()
    }


    @Test
    fun testRecyclerViewFavoritesDisplayed() {
        onView(withId(R.id.recyclerViewFavorites))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSwipeToDeleteCrypto() {
        // Assuming there's at least one item in the list to swipe
        onView(withId(R.id.recyclerViewFavorites))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeLeft()))

        // If you expect the item to be removed after swiping, you can add a delay then check if item count is reduced.
        // For better results, use Idling Resources instead of Thread.sleep.
        // Here, just as a simple demonstration, I've used Thread.sleep:
        Thread.sleep(1000)

        // You can further check if the item is no longer displayed if you have unique text or identifiers for items.
    }

    // Additional tests...
}
