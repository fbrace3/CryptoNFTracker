package edu.msudenver.cs3013.project03

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(RegisterActivity::class.java, true, false  )

    @Before
    fun setup() {
        activityRule.launchActivity(null) // Launch the RegisterActivity
    }
    @After
    fun tearDown() {
        Intents.release()
    }
    @Test
    fun testFirstNameEditTextDisplayed() {
        onView(withId(R.id.first_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLastNameEditTextDisplayed() {
        onView(withId(R.id.last_name))
            .check(matches(isDisplayed()))
    }

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
    fun testConfirmPasswordEditTextDisplayed() {
        onView(withId(R.id.confirmPassword))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEmailAddressEditTextDisplayed() {
        onView(withId(R.id.email_address))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRegisterButtonDisplayed() {
        onView(withId(R.id.register_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testValidRegistrationInput() {

        onView(withId(R.id.first_name))
            .perform(typeText("John"), closeSoftKeyboard())

        onView(withId(R.id.last_name))
            .perform(typeText("Doe"), closeSoftKeyboard())

        onView(withId(R.id.user_name))
            .perform(typeText("johndoe"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.confirmPassword))
            .perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.email_address))
            .perform(typeText("johndoe@example.com"), closeSoftKeyboard())

        onView(withId(R.id.register_button))
            .perform(click())

    }


    @Test
    fun testMismatchPasswordRegistration() {
        onView(withId(R.id.first_name))
            .perform(typeText("John"), closeSoftKeyboard())

        onView(withId(R.id.last_name))
            .perform(typeText("Doe"), closeSoftKeyboard())

        onView(withId(R.id.user_name))
            .perform(typeText("johndoe"), closeSoftKeyboard())

        onView(withId(R.id.password))
            .perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.confirmPassword))
            .perform(typeText("password456"), closeSoftKeyboard())

        onView(withId(R.id.email_address))
            .perform(typeText("johndoe@example.com"), closeSoftKeyboard())

        onView(withId(R.id.register_button))
            .perform(click())
        // After this, you can further assert that an error toast is displayed.
    }

    // You can further extend these tests to include more scenarios.

}
