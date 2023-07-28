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
    fun testSubmitButtonDisplayed() {
        onView(withId(R.id.submit_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testUsernamePasswordEditTextDisplayed() {
        onView(withId(R.id.user_name))
            .check(matches(isDisplayed()))

        onView(withId(R.id.password))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testClickingRegisterOpensRegisterActivity() {
        onView(withId(R.id.register_button))
            .perform(click())
        onView(withId(R.id.first_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testUsernamePasswordInput() {
        onView(withId(R.id.user_name))
            .perform(typeText("TestUser"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(typeText("password123"), closeSoftKeyboard())
    }

    // ... Additional tests for LoginActivity
}

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun testRegisterButtonDisplayed() {
        onView(withId(R.id.register_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFirstNameLastNameEditTextDisplayed() {
        onView(withId(R.id.first_name))
            .check(matches(isDisplayed()))

        onView(withId(R.id.last_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFirstNameLastNameInput() {
        onView(withId(R.id.first_name))
            .perform(typeText("John"), closeSoftKeyboard())

        onView(withId(R.id.last_name))
            .perform(typeText("Doe"), closeSoftKeyboard())
    }

    // ... Add other tests for RegisterActivity
}

@RunWith(AndroidJUnit4::class)
class WelcomeActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(WelcomeActivity::class.java)

    @Test
    fun testHeaderDisplayed() {
        onView(withId(R.id.header))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLogoutButtonDisplayed() {
        onView(withId(R.id.back_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testClickingLogoutReturnsToLogin() {
        onView(withId(R.id.back_button))
            .perform(click())

        onView(withId(R.id.user_name))
            .check(matches(isDisplayed())) // Assuming R.id.username is in LoginActivity
    }

    // ... Add other tests for WelcomeActivity
}
