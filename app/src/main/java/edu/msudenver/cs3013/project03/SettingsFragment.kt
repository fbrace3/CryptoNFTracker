package edu.msudenver.cs3013.project03

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.ByteArrayOutputStream

class SettingsFragment : Fragment() {
//    private var username: String? = null
    private lateinit var photoVerify: ImageView
    private lateinit var settingText: TextView
    private lateinit var preference1: TextView
    private lateinit var preference2: TextView
    private lateinit var preference3: TextView
    private lateinit var preference4: TextView
    private lateinit var verifyButton: Button
    private lateinit var logoutButton: Button

    override  fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Retrieve the bundle from the arguments of the navigation graph


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the user data from arguments
        val user = arguments?.getSerializable("myUser") as User?
        val capturedBitmap = arguments?.getParcelable<Bitmap>("capturedBitmap")
        if (user != null) {
            // Use the user data as needed
            // For example:
            val username = user.username
            val firstname = user.firstName
            val lastname = user.lastName
            val email = user.emailAddress
            // Update UI elements with user data, e.g., textView.text = "Username: $username"
        }
        photoVerify = view.findViewById(R.id.setting_image)
        settingText = view.findViewById(R.id.setting_text)
        preference1 = view.findViewById(R.id.preference_header)
        preference2 = view.findViewById(R.id.preference_header2)
        preference3 = view.findViewById(R.id.preference_header3)
        preference4 = view.findViewById(R.id.preference_header4)
        verifyButton = view.findViewById(R.id.verify_id)
        logoutButton = view.findViewById(R.id.logout_button)


        // Update the TextViews with user data
        val settingsMessage = getString(R.string.settings_fragment, user?.username)
        settingText.text = settingsMessage
        preference3.text = "Username: " + user?.username
        preference1.text = "First Name: " + user?.firstName
        preference2.text = "Last Name: " + user?.lastName
        preference4.text = "Email: " + user?.emailAddress

        // TODO Data persistence attempt
        val sharedPreferences: SharedPreferences? =
            this.activity?.getSharedPreferences("imagePreference", Context.MODE_PRIVATE)

        if (sharedPreferences?.getString("image", null) == null) {
            capturedBitmap?.let {
                setCapturedImage(it)
                sharedPreferences?.edit()?.putString("image", capturedBitmap?.let { encodeImageToBase64(it) })?.apply()
                sharedPreferences?.edit()?.putBoolean("verified", true)?.apply()
            }


        }
        else {
            sharedPreferences?.getString("image", null)?.let { decodeBase64ToImage(it) }?.let { setCapturedImage(it) }
            //user?.profileImage?.let { decodeBase64ToImage(it) }?.let { setCapturedImage(it) }
        }
        //set click listener for verify button
        verifyButton.setOnClickListener {
            //navigate to photo verify fragment
            val photoVerifyFragment = PhotoVerifyFragment()
            // Replace R.id.nav_photo_verify with the ID of the PhotoVerifyFragment destination
            findNavController().navigate(R.id.nav_photo_verify)

        }
        logoutButton.setOnClickListener {
            signOut()
        }
        //if the user has already verified their identity, hide the verify button
        if (sharedPreferences?.getBoolean("verified", false) == true) {
            verifyButton.visibility = View.GONE
            //place a green checkmark where the verify button was
            val checkmark = view.findViewById<ImageView>(R.id.verified)
            val isVerified = view.findViewById<TextView>(R.id.verified_text)
            checkmark.visibility = View.VISIBLE
            isVerified.visibility = View.VISIBLE
        }


    }
    private fun signOut() {
        // Clear user session (e.g., clear tokens, preferences, cached data)
        clearUserSession()

        // Navigate to the sign-in screen and clear the fragment backstack
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        // Alternatively, if you're using Navigation Components:
        // findNavController().navigate(R.id.action_global_signInFragment)
    }
    private fun clearUserSession() {
        // Clear user session (e.g., clear tokens, preferences, cached data)
        // TODO Clear user session
    }
    fun setCapturedImage(bitmap: Bitmap?) {
        Log.d("Line 108", encodeImageToBase64(bitmap!!))
        //update the image in the user object
        val user = arguments?.getSerializable("myUser") as User?
        user?.profileImage = bitmap?.let { encodeImageToBase64(it) }
        photoVerify.setImageBitmap(bitmap)
        // Handle the logic to set the captured image in the SettingsFragment
        // For example, you can set the bitmap to an ImageView in the SettingsFragment
        // imageView.setImageBitmap(bitmap)
        // Replace "imageView" with the actual ID of the ImageView in the SettingsFragment layout
    }
    fun encodeImageToBase64(imageBitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun decodeBase64ToImage(base64String: String?): Bitmap {
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = SettingsFragment().apply {
            arguments = Bundle().apply {
                putBundle("myUserBundle", bundle)
            }
        }
    }
}
