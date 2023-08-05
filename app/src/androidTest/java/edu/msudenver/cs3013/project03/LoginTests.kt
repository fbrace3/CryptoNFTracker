package edu.msudenver.cs3013.project03

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
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
        // Step 1: Initialize the Intents capturing
        Intents.init()

        // Perform the actions on the views as before
        onView(withId(R.id.user_name))
            .perform(typeText("TestUser"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.submit_button))
            .perform(click())

        // Step 3: Check the intent after clicking the button
        intended(hasComponent(WelcomeActivity::class.java.name))

        // Step 4: Release the Intents after the test finishes
        Intents.release()
    }

    // You can add more tests for other functionalities, error scenarios, etc.

}
