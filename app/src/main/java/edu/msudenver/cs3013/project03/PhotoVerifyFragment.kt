package edu.msudenver.cs3013.project03

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.ByteArrayOutputStream


class PhotoVerifyFragment : Fragment() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSION = 2
    }

    private lateinit var capturedImage: ImageView
    private lateinit var captureButton: Button
    private lateinit var sendButton: Button
    private var capturedBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_verify, container, false)
        capturedImage = view.findViewById(R.id.captured_image)
        captureButton = view.findViewById(R.id.capture_button)
        sendButton = view.findViewById(R.id.send_button)

        captureButton.setOnClickListener {
            takePicture()
        }

        sendButton.setOnClickListener {
            sendPhotoToSettings()
        }

        return view
    }

    private fun takePicture() {
        // Check if the camera permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted, proceed to take a picture
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // Try to find the front-facing camera
            val packageManager = requireActivity().packageManager
            val resolveInfo = packageManager.resolveActivity(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY)
            val packageName = resolveInfo?.activityInfo?.packageName

            // Check if the front-facing camera is available
            if (packageName != null && packageName.contains("com.android.camera")) {
                // Set the extra parameter to indicate that we want to use the front-facing camera
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1)
            }

            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }



    private fun sendPhotoToSettings() {
        //update the image in the user object


        capturedBitmap?.let { bitmap ->
            val settingsFragment = SettingsFragment()
            val bundle = Bundle()
            bundle.putParcelable("capturedBitmap", bitmap)
            settingsFragment.arguments = bundle

            var user = arguments?.getSerializable("myUser") as User?
            user?.verified = true

            // Assuming you are using a NavController to navigate between fragments
            // Replace R.id.nav_settings with the ID of the SettingsFragment destination
            findNavController().navigate(R.id.nav_settings, bundle)

        }
        //update the image in the user object

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            data?.extras?.get("data")?.let {
                capturedBitmap = it as Bitmap
                capturedImage.setImageBitmap(capturedBitmap)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed to take a picture
                takePicture()
            } else {
                // Camera permission denied, show a message to the user (optional)
                // You can also handle this case by disabling the capture button or requesting the permission again
            }
        }
    }


    // Add this function to the PhotoVerifyFragment or a separate Utils class
    fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
