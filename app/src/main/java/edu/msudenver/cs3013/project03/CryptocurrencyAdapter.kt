package edu.msudenver.cs3013.project03
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CryptocurrencyAdapter(
    private val cryptocurrencies: MutableList<Cryptocurrency>,
    private val onAddButtonClickListener: (Cryptocurrency) -> Unit,
    private val showAddButton: Boolean = true
) : RecyclerView.Adapter<CryptocurrencyAdapter.CryptocurrencyViewHolder>() {

    class CryptocurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textSymbol: TextView = itemView.findViewById(R.id.textSymbol)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
        val btnAdd: Button = itemView.findViewById(R.id.btnAdd) // Add button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cryptocurrency, parent, false)
        return CryptocurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        val cryptocurrency = cryptocurrencies[position]
        holder.textName.text = "Name: ${cryptocurrency.name}"
        holder.textSymbol.text = "Symbol: ${cryptocurrency.symbol}"
        holder.textPrice.text = "Price: ${cryptocurrency.quote.USD.price}"

        // Set click listener for the "Add" button
        if (showAddButton) {
            holder.btnAdd.visibility = View.VISIBLE
            holder.btnAdd.setOnClickListener {
                onAddButtonClickListener(cryptocurrency)
            }
        } else {
            holder.btnAdd.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return cryptocurrencies.size
    }

    fun removeItem(cryptocurrency: Cryptocurrency) {
        val position = cryptocurrencies.indexOf(cryptocurrency)
        if (position != -1) {
            cryptocurrencies.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
