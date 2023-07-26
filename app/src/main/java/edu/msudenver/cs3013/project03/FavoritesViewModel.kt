package edu.msudenver.cs3013.project03

import androidx.lifecycle.ViewModel
import edu.msudenver.cs3013.project03.Cryptocurrency

class FavoritesViewModel : ViewModel() {
    // List of selected cryptocurrencies (favorites)
    val favoritesList = mutableListOf<Cryptocurrency>()
    fun addToFavorites(cryptocurrency: Cryptocurrency) {
        favoritesList.add(cryptocurrency)
    }
}
