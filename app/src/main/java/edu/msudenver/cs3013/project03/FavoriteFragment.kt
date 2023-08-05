package edu.msudenver.cs3013.project03
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewNft: RecyclerView

    private lateinit var headerFavorite: TextView
    private lateinit var headerNftFavorite: TextView

    private lateinit var adapter: CryptocurrencyAdapter
    private lateinit var adapterNft: NftAdapter

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

        val user = arguments?.getSerializable("myUser") as User?
        if (user != null) {
            // Use the user data as needed
            // For example:
            val username = user.username
            val firstname = user.firstName
            val lastname = user.lastName
            val email = user.emailAddress
            // Update UI elements with user data, e.g., textView.text = "Username: $username"
        }

        val CryptoHeader = getString(R.string.crypto_Favorites, user?.username)
        val NFTHeader = getString(R.string.nft_Favorites, user?.username)
        headerFavorite = view.findViewById(R.id.headerFavorite)
        headerNftFavorite = view.findViewById(R.id.headerFavoriteNft)

        headerFavorite.text = CryptoHeader
        headerNftFavorite.text = NFTHeader

        recyclerView = view.findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerViewNft = view.findViewById(R.id.recyclerViewNftFavorites)
        recyclerViewNft.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the CryptocurrencyAdapter
        val adapter = CryptocurrencyAdapter(favoritesViewModel.favoritesList.value ?: mutableListOf(), {}, false)
        recyclerView.adapter = adapter

        // Observe changes to the LiveData and update the adapter
        favoritesViewModel.favoritesList.observe(viewLifecycleOwner, Observer { favoritesList ->
            adapter.cryptocurrencies = favoritesList
            adapter.notifyDataSetChanged()
        })

        // Attach the SwipeToDeleteCallback
        val cryptoSwipeHandler = SwipeToDeleteCallback(adapter, favoritesViewModel)
        val cryptoItemTouchHelper = ItemTouchHelper(cryptoSwipeHandler)
        cryptoItemTouchHelper.attachToRecyclerView(recyclerView)

        // Initialize the NftAdapter
        val adapterNft = NftAdapter(favoritesViewModel.nftFavoritesList.value ?: mutableListOf(), {}, false)
        recyclerViewNft.adapter = adapterNft

        // Observe changes to the LiveData and update the adapter
        favoritesViewModel.nftFavoritesList.observe(viewLifecycleOwner, Observer { nftFavoritesList ->
            adapterNft.nftData = nftFavoritesList
            adapterNft.notifyDataSetChanged()
        })

        // Attach the SwipeToDeleteCallback
        val nftSwipeHandler = SwipeToDeleteCallback(adapterNft, favoritesViewModel)
        val nftItemTouchHelper = ItemTouchHelper(nftSwipeHandler)
        nftItemTouchHelper.attachToRecyclerView(recyclerViewNft)
    }

    private class SwipeToDeleteCallback(
        private val adapter: RecyclerView.Adapter<*>,
        private val favoritesViewModel: FavoritesViewModel
    ) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

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
                    val item = adapter.getItemAtPosition(position)
                    favoritesViewModel.removeFromFavorites(item)
                }
                is NftAdapter -> {
                    val item = adapter.getItemAtPosition(position)
                    favoritesViewModel.removeFromNftFavorites(item)
                }
            }
        }
    }
}
