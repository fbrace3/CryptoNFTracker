package edu.msudenver.cs3013.project03

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

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

        // Initialize the CryptocurrencyAdapter
        // Note: We're passing `false` for `showAddButton` argument to hide the "Add" button.
        // We also provide an empty lambda {} since there's no action required on button click.
        val adapter = CryptocurrencyAdapter(favoritesViewModel.favoritesList.toMutableList(), {}, false)
        recyclerView.adapter = adapter
    }
}
