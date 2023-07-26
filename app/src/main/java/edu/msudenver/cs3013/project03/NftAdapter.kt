package edu.msudenver.cs3013.project03

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class NftAdapter(
    private val nftData: List<NftItem>,
    private val onAddButtonClickListener: (NftItem) -> Unit,
    private val showAddButton: Boolean = true
) : RecyclerView.Adapter<NftAdapter.NftViewHolder>() {

    class NftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.nftImage)
        val textPrice: TextView = itemView.findViewById(R.id.textPriceNft)
        val btnAdd: Button = itemView.findViewById(R.id.btnAddNft)
        val btnBuy: Button = itemView.findViewById(R.id.btnBuyNft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nft, parent, false)
        return NftViewHolder(itemView)
    }

    override fun onBindViewHolder(nftHolder: NftViewHolder, position: Int) {
        val profileImageView: ImageView = nftHolder.image
        val nftPrice: TextView = nftHolder.textPrice
        val defaultImageResId = R.drawable.money
        val imageLoader: ImageLoader by lazy {
            GlideImageLoader(nftHolder.itemView.context)
        }
        val nftItem = nftData[position]
        val price = nftItem.recent_price?.price_usd ?: "N/A"
        val priceDouble = price as? Double ?: 0.0
        val decimalFormat = DecimalFormat("#.00")
        val formattedPrice = decimalFormat.format(priceDouble)
        nftPrice.text = "$ ${formattedPrice}"

        if (nftItem.cached_images?.tiny_100_100 != null) {
            imageLoader.loadImage(nftItem.cached_images.tiny_100_100, profileImageView)
        } else {
            profileImageView.setImageResource(defaultImageResId)
        }

        if (showAddButton) {
            nftHolder.btnAdd.visibility = View.VISIBLE
            nftHolder.btnAdd.setOnClickListener {
                onAddButtonClickListener(nftItem)
            }
        } else {
            nftHolder.btnAdd.visibility = View.GONE
        }

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

    override fun getItemCount(): Int {
        return nftData.size
    }
}
