package edu.msudenver.cs3013.project03

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalMatchers.not

@RunWith(AndroidJUnit4::class)
class FavoriteFragmentTest {

    private lateinit var scenario: FragmentScenario<FavoriteFragment>

    @Before
    fun setUp() {
        // Given a fresh FavoriteFragment
        scenario = launchFragmentInContainer()
    }

    @Test
    fun testFragmentIsLoaded() {
        onView(withId(R.id.recyclerViewFavorites)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerViewNftFavorites)).check(matches(isDisplayed()))
    }

    @Test
    fun testCryptocurrencyIsDisplayed() {
        onView(withId(R.id.recyclerViewFavorites))
            .check(matches(hasDescendant(withText("Bitcoin"))))  // You need to replace the placeholder text with an actual item from your data set
    }

    @Test
    fun testSwipeToRemoveWorks() {
        // Assuming there's data in the adapter
        onView(withId(R.id.recyclerViewFavorites)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeLeft())
        )

        // Verify if the item is removed - this can be a check if the removed item is no longer displayed
        onView(withId(R.id.recyclerViewFavorites))
            .check(matches(not(hasDescendant(withText("Bitcoin"))))) // Replace with the actual name/text of the removed item
    }
}
