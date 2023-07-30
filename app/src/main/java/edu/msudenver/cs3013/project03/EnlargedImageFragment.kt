package edu.msudenver.cs3013.project03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class EnlargedNftImageFragment : DialogFragment() {

    companion object {
        private const val ARG_IMAGE_URL = "image_url"

        @JvmStatic
        fun newInstance(imageUrl: String): EnlargedNftImageFragment {
            val fragment = EnlargedNftImageFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URL, imageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_enlarged_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = arguments?.getString(ARG_IMAGE_URL)
        // Load and display the enlarged NFT image using an image loading library like Glide or Picasso
        // For example:
        // Glide.with(this).load(imageUrl).into(enlargedImageView)

        // You need to have an ImageView with id "enlargedImageView" in your layout (fragment_enlarged_nft_image.xml)
    }
}
