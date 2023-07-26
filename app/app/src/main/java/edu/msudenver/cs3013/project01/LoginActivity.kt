package edu.msudenver.cs3013.project03

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class LoginActivity : AppCompatActivity() {

    // Properties for UI elements
    private val submitButton: Button
        get() = findViewById(R.id.submit_button)
    private val registerButton: Button
        get() = findViewById(R.id.register_button)
    private val userName: EditText
        get() = findViewById(R.id.user_name)
    private val password: EditText
        get() = findViewById(R.id.password)

    // Properties for registered user data
    private var registeredUsername: String? = ""
    private var registeredPassword: String? = ""
    private var registeredUser = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize click listeners for buttons
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                registeredUser = data?.getSerializableExtra("myUser") as User
                registeredUsername = registeredUser.username
                registeredPassword = registeredUser.password

                userName.setText(registeredUsername)
                password.setText(registeredPassword)
            }
        }
        registerButton.setOnClickListener {
            startForResult.launch(Intent(this, RegisterActivity::class.java))
        }

        submitButton.setOnClickListener {
            var userNameForm = userName.text.toString().trim()
            var passwordForm = password.text.toString().trim()

            hideKeyboard()

            if (userNameForm.isNotEmpty() && passwordForm.isNotEmpty()) {
                Intent(this, WelcomeActivity::class.java).also { welcomeIntent ->
                    welcomeIntent.putExtra("myUser", registeredUser)
                    welcomeIntent.putExtra("USER_NAME_KEY", userNameForm)
                    welcomeIntent.putExtra("PASSWORD_KEY", passwordForm)

                    this.userName.text.clear()
                    this.password.text.clear()

                    startActivity(welcomeIntent)
                }
            } else {
                val toast = Toast.makeText(
                    this,
                    getString(R.string.login_form_entry_error),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }

    // Hide the keyboard
    private fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}
