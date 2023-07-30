package edu.msudenver.cs3013.project03

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeActivityTest {

    @Test
    fun testWelcomeMessageDisplayedForValidLogin() {
        val validUser = User("testUser", "testPass", "testFirst", "testLast", "testEmail")
        val intent = Intent(ApplicationProvider.getApplicationContext(), WelcomeActivity::class.java).apply {
            putExtra("myUser", validUser)
            putExtra("USER_NAME_KEY", "testUser")
            putExtra("PASSWORD_KEY", "testPass")
        }
        ActivityScenario.launch<WelcomeActivity>(intent)

        onView(withId(R.id.header))
            .check(matches(withText("Welcome to our Crypto/Nft Tracking App testUser!")))
    }

    @Test
    fun testErrorDisplayedForInvalidLogin() {
        val validUser = User("testUser", "testPass", "testFirst", "testLast", "testEmail")
        val intent = Intent(ApplicationProvider.getApplicationContext(), WelcomeActivity::class.java).apply {
            putExtra("myUser", validUser)
            putExtra("USER_NAME_KEY", "wrongUser")
            putExtra("PASSWORD_KEY", "wrongPass")
        }
        ActivityScenario.launch<WelcomeActivity>(intent)

        onView(withId(R.id.header))
            .check(matches(withText(R.string.login_error)))
    }

    @Test
    fun testContinueButtonOpensMainActivityForValidLogin() {
        val validUser = User("testUser", "testPass", "testFirst", "testLast", "testEmail")
        val intent = Intent(ApplicationProvider.getApplicationContext(), WelcomeActivity::class.java).apply {
            putExtra("myUser", validUser)
            putExtra("USER_NAME_KEY", "testUser")
            putExtra("PASSWORD_KEY", "testPass")
        }
        ActivityScenario.launch<WelcomeActivity>(intent)

        onView(withId(R.id.continue_button))
            .check(matches(isDisplayed()))
            .perform(click())

        // Check if favoriteFragment is opened, for now, we can assume that favoriteFragment has a specific view with a specific id.
        onView(withId(R.id.headerFavorite))
            .check(matches(isDisplayed()))
    }

    // You can further extend these tests to include more scenarios based on other features of the activity.

}
