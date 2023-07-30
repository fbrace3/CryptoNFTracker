package edu.msudenver.cs3013.project03

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testBottomNavigationViewIsDisplayed() {
        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationUsingDrawerMenu() {
        // Open the drawer
//        onView(withId(R.id.drawer_layout)).perform(click())
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())


        // Click on the 'nav_coin_fragment' item in the NavigationView
        onView(allOf(withId(R.id.nav_coin_fragment), isDescendantOfA(withId(R.id.nav_view)))).perform(click())

        // Check if the appropriate fragment is displayed (use a view ID or content from that fragment)
         onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

//    @Test
//    fun testNavigationUsingBottomNavigationView() {
//        // Click on an item in the BottomNavigationView
//        onView(allOf(withId(R.id.nav_BankFinderFragment), isDescendantOfA(withId(R.id.nav_view)))).perform(click())
//
//        // Check if the appropriate fragment is displayed (use a view ID or content from that fragment)
//         onView(withId(R.id.map)).check(matches(isDisplayed()))
//    }
}
