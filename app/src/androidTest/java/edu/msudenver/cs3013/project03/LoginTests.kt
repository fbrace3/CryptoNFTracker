package edu.msudenver.cs3013.project03

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testUsernameEditTextDisplayed() {
        onView(withId(R.id.user_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testPasswordEditTextDisplayed() {
        onView(withId(R.id.password))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSubmitButtonDisplayed() {
        onView(withId(R.id.submit_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRegisterButtonDisplayed() {
        onView(withId(R.id.register_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testUsernamePasswordInput() {
        onView(withId(R.id.user_name))
            .perform(typeText("TestUser"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(typeText("password123"), closeSoftKeyboard())
    }

    @Test
    fun testClickingRegisterOpensRegisterActivity() {
        onView(withId(R.id.register_button))
            .perform(click())
        // You can then assert that a specific view from the RegisterActivity is displayed
        // using a similar check(matches(isDisplayed())) logic.
        // This will confirm that the RegisterActivity is launched.
    }

    @Test
    fun testClickingSubmitOpensWelcomeActivity() {
        // Assuming valid username and password are filled in:
        onView(withId(R.id.user_name))
            .perform(typeText("TestUser"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.submit_button))
            .perform(click())
        // You can then assert that a specific view from the WelcomeActivity is displayed
        // to confirm that the WelcomeActivity is launched.
    }

    // You can add more tests for other functionalities, error scenarios, etc.

}
