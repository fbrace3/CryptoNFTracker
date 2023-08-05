package edu.msudenver.cs3013.project03

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testNavigationDrawerOpens() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationFromNavDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_nft_fragment))
        // Assuming you have a unique view in nav_nft_fragment to check against:
        onView(withId(R.id.recyclerViewNft)).check(matches(isDisplayed()))
    }

    @Test
    fun testBottomNavigationIsDisplayed() {
        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationFromBottomNavigation() {
        onView(allOf(withId(R.id.nav_nft_fragment), isDisplayed())).perform(click())

        // Again, assuming you have a unique view in the nav_nft_fragment to check against:
        onView(withId(R.id.recyclerViewNft)).check(matches(isDisplayed()))
    }
}
