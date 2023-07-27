package edu.msudenver.cs3013.project03

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewNft: RecyclerView

    // Use the shared ViewModel
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerViewNft = view.findViewById(R.id.recyclerViewNftFavorites)
        recyclerViewNft.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the CryptocurrencyAdapter
        // Note: We're passing `false` for `showAddButton` argument to hide the "Add" button.
        val adapter = CryptocurrencyAdapter(favoritesViewModel.favoritesList.toMutableList(), {}, false)
        recyclerView.adapter = adapter

        val adapterNft = NftAdapter(favoritesViewModel.nftfavoritesList.toMutableList(), {}, false)
        recyclerViewNft.adapter = adapterNft

        // Attach the SwipeToDeleteCallback
        val cryptoSwipeHandler = SwipeToDeleteCallback(adapter)
        val cryptoItemTouchHelper = ItemTouchHelper(cryptoSwipeHandler)
        cryptoItemTouchHelper.attachToRecyclerView(recyclerView)

        val nftSwipeHandler = SwipeToDeleteCallback(adapterNft)
        val nftItemTouchHelper = ItemTouchHelper(nftSwipeHandler)
        nftItemTouchHelper.attachToRecyclerView(recyclerViewNft)
    }

    private class SwipeToDeleteCallback(private val adapter: RecyclerView.Adapter<*>) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            when (adapter) {
                is CryptocurrencyAdapter -> {
                    val item = (adapter as CryptocurrencyAdapter).getItemAtPosition(position)
                    (adapter as CryptocurrencyAdapter).removeItem(item)
                }
                is NftAdapter -> {
                    val item = (adapter as NftAdapter).getItemAtPosition(position)
                    (adapter as NftAdapter).removeItem(item)
                }
            }
        }
    }
}
