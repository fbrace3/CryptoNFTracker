package edu.msudenver.cs3013.project03

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private val registerButton: Button
        get() = findViewById(R.id.register_button)
    private val firstName: EditText
        get() = findViewById(R.id.first_name)
    private val lastName: EditText
        get() = findViewById(R.id.last_name)
    private val userName: EditText
        get() = findViewById(R.id.user_name)
    private val password: EditText
        get() = findViewById(R.id.password)
    private val confirmPassword: EditText
        get() = findViewById(R.id.confirmPassword)
    private val emailAddress: EditText
        get() = findViewById(R.id.email_address)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val myUser = User()

        //        if (userName != null) {

        registerButton.setOnClickListener {

            var firstNameForm = firstName.text.toString().trim()
            var lastNameForm = lastName.text.toString().trim()
            var userNameForm = userName.text.toString().trim()
            var passwordForm = password.text.toString().trim()
            var confirmPasswordForm = confirmPassword.text.toString().trim()
            var emailAddressForm = emailAddress.text.toString().trim()


            hideKeyboard()

            if (firstNameForm.isNotEmpty() && passwordForm.isNotEmpty() && lastNameForm.isNotEmpty() && userNameForm.isNotEmpty() && passwordForm == confirmPasswordForm) {

                myUser.firstName = firstNameForm
                myUser.lastName = lastNameForm
                myUser.username = userNameForm
                myUser.password = passwordForm
                myUser.emailAddress = emailAddressForm

                Intent(this, LoginActivity::class.java).also { loginIntent ->
                    //TODO: May be able to remove these two lines
//                    loginIntent.putExtra("myUsername", myUser.username)
//                    loginIntent.putExtra("myPassword", myUser.password)
                    loginIntent.putExtra("myUser", myUser)
                    this.userName.text.clear()
                    this.password.text.clear()
                    this.confirmPassword.text.clear()
                    this.firstName.text.clear()
                    this.lastName.text.clear()
                    this.emailAddress.text.clear()
                    setResult(
                        Activity.RESULT_OK,
                        loginIntent)
                    val toast = Toast.makeText(
                        this,
                        getString(R.string.register_success),
                        Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    finish()
                }

                // TODO: This could be removed
//                Intent(this, WelcomeActivity::class.java).also { welcomeIntent ->
//                    //Add the data
//                    welcomeIntent.putExtra("myUser", myUser)
//
//                    //Reset text fields to blank
//                    this.userName.text.clear()
//                    this.password.text.clear()
//                    this.confirmPassword.text.clear()
//                    this.firstName.text.clear()
//                    this.lastName.text.clear()
//                    this.age.text.clear()
//                    this.favoriteInstrument.text.clear()
//
//                    //Launch
//                    startActivity(welcomeIntent)
//                }


            } else {
                val toast = Toast.makeText(
                    this,
                    getString(R.string.register_form_entry_error),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }

        }

    }
    private fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}