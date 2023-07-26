package edu.msudenver.cs3013.project03



import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class WelcomeActivity : AppCompatActivity() {

    var isLoggedIn = false
    var loggedInUser: String? = ""
    var loggedInPassword: String? = ""


    private val header: TextView
        get() = findViewById(R.id.header)

    private val backButton: Button
        get() = findViewById(R.id.back_button)
    private val continueButton: Button
        get() = findViewById(R.id.continue_button)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //Get the intent which started this activity
        intent.let { loginIntent ->
            val passedUser = loginIntent?.getSerializableExtra("myUser") as User?
            val userNameForm = loginIntent?.getStringExtra("USER_NAME_KEY") ?: ""
            val passwordForm = loginIntent?.getStringExtra("PASSWORD_KEY") ?: ""
            Log.d("Logged in Username", passedUser?.username.toString())
            Log.d("Logged in Password", passedUser?.password.toString())
//            val registeredUsername = passedUser?.username
//            val registeredPassword = passedUser?.password

            val loggedInCorrectly =
                hasEnteredCorrectCredentials(userNameForm.trim(), passwordForm.trim(),
                    passedUser?.username, passedUser?.password)

            Log.d("Logged in correctly", loggedInCorrectly.toString())
            if (loggedInCorrectly) {
                setLoggedIn(passedUser?.username)
                loggedInUser = passedUser?.username
                loggedInPassword = passedUser?.password
                isLoggedIn = true
                continueButton.isVisible = true
                continueButton.setOnClickListener{
                    Intent(this, MainActivity::class.java).also { mainIntent ->
                        setResult(
                            Activity.RESULT_OK,
                            mainIntent)
                        mainIntent.putExtra("myUser", passedUser)
                        startActivity(mainIntent)
                    }}

            } else {
                header.text = getString(R.string.login_error)
                backButton.isVisible = true
                backButton.setOnClickListener {
                    //Finishes this activity and so goes back to the previous activity
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("IS_LOGGED_IN", isLoggedIn)
        outState.putString("LOGGED_IN_USERNAME", loggedInUser)
        outState.putString("LOGGED_IN_PASSWORD", loggedInPassword)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        isLoggedIn = savedInstanceState.getBoolean("IS_LOGGED_IN", false)
        loggedInUser = savedInstanceState.getString("LOGGED_IN_USERNAME", "")

        if (isLoggedIn) {
            setLoggedIn(loggedInUser)
        }
    }

    private fun setLoggedIn(userName: String?) {
        loggedInUser = userName
        val welcomeMessage = getString(R.string.welcome_message, userName)
        backButton.isVisible = false
        header.text = welcomeMessage
    }

    private fun hasEnteredCorrectCredentials(
        userNameForm: String,
        passwordForm: String,
        userNameCorrect: String?,
        passwordCorrect: String?
    ): Boolean {
        return userNameForm.contentEquals(userNameCorrect) && passwordForm.contentEquals(
            passwordCorrect
        )
    }
}