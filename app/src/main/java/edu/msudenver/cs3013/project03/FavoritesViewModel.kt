package edu.msudenver.cs3013.project03

import androidx.lifecycle.ViewModel

class FavoritesViewModel : ViewModel() {
    // List of selected cryptocurrencies (favorites)
    val favoritesList = mutableListOf<Cryptocurrency>()
    val nftfavoritesList = mutableListOf<NftItem>()
    fun addToFavorites(cryptocurrency: Cryptocurrency) {
        favoritesList.add(cryptocurrency)
    }
    fun addToNftFavorites(nftData: NftItem) {
        nftfavoritesList.add(nftData)
    }
}
