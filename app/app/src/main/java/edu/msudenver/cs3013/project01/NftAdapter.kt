package edu.msudenver.cs3013.project03

import android.icu.text.DecimalFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView

class NftAdapter(private val nftData: List<NftItem>?) :
    RecyclerView.Adapter<NftAdapter.NftViewHolder>() {


    class NftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.nftImage)
        val textPrice: TextView = itemView.findViewById(R.id.textPriceNft)
        val btnAdd: Button = itemView.findViewById(R.id.btnAddNft) //
       val btnBuy: Button = itemView.findViewById(R.id.btnBuyNft) //
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nft, parent, false)
        return NftViewHolder(itemView)
    }

    override fun onBindViewHolder(nftHolder: NftViewHolder, position: Int) {
        val profileImageView: ImageView = nftHolder.itemView.findViewById(R.id.nftImage)
        val nftPrice: TextView = nftHolder.itemView.findViewById(R.id.textPriceNft)
        val defaultImageResId = R.drawable.money
        val imageLoader: ImageLoader by lazy {
            GlideImageLoader(nftHolder.itemView.context)
        }
        val nftData = nftData?.get(position)
        val price = nftData?.recent_price?.price_usd ?: "N/A" // Replace "N/A" with any default text for null prices
        val priceDouble = price as? Double ?: 0.0
        val decimalFormat = DecimalFormat("#.00")
        val formattedPrice = decimalFormat.format(priceDouble)
        nftPrice.text = "$ ${formattedPrice}"
        // Check if the image URL is not null
        if (nftData?.cached_images?.tiny_100_100 != null) {
            // If the image URL is not null, load the image using the URL
            imageLoader.loadImage(nftData.cached_images.tiny_100_100, profileImageView)
        } else {
            // If the image URL is null, set the default image
            profileImageView.setImageResource(defaultImageResId)
        }

        nftHolder.btnAdd.setOnClickListener {
            // Call the click listener with the corresponding cryptocurrency object
//            onAddButtonClickListener(cryptocurrency)
        }
        nftHolder.btnBuy.setOnClickListener {
            nftHolder.btnBuy.setOnClickListener {
                // Create and show the AlertDialog
                val context = nftHolder.itemView.context
                AlertDialog.Builder(context)
                    .setTitle("Notice")
                    .setMessage("This function is not available yet")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun getItemCount(): Int {
        return nftData?.size ?: 0
    }
}