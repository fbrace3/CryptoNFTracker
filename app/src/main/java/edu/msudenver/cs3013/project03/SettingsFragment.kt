package edu.msudenver.cs3013.project03

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SettingsFragment : Fragment() {
//    private var username: String? = null
    private lateinit var photoVerify: ImageView
    private lateinit var settingText: TextView
    private lateinit var preference1: TextView
    private lateinit var preference2: TextView
    private lateinit var preference3: TextView
    private lateinit var preference4: TextView
    private lateinit var verifyButton: Button

    override fun onCreateView(
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


        // Update the TextViews with user data
        val settingsMessage = getString(R.string.settings_fragment, user?.username)
        settingText.text = settingsMessage
        preference3.text = "Username: " + user?.username
        preference1.text = "First Name: " + user?.firstName
        preference2.text = "Last Name: " + user?.lastName
        preference4.text = "Email: " + user?.emailAddress

        capturedBitmap?.let {
            setCapturedImage(it)
        }

        //set click listener for verify button
        verifyButton.setOnClickListener {
            //navigate to photo verify fragment
            val photoVerifyFragment = PhotoVerifyFragment()
            // Replace R.id.nav_photo_verify with the ID of the PhotoVerifyFragment destination
            findNavController().navigate(R.id.nav_photo_verify)

        }
    }
    fun setCapturedImage(bitmap: Bitmap) {
        photoVerify.setImageBitmap(bitmap)
        // Handle the logic to set the captured image in the SettingsFragment
        // For example, you can set the bitmap to an ImageView in the SettingsFragment
        // imageView.setImageBitmap(bitmap)
        // Replace "imageView" with the actual ID of the ImageView in the SettingsFragment layout
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
