package edu.msudenver.cs3013.project03
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoritesViewModel : ViewModel() {
    // Use LiveData for the lists of selected cryptocurrencies (favorites)
    private val _favoritesList = MutableLiveData<MutableList<Cryptocurrency>>(mutableListOf())
    private val _nftFavoritesList = MutableLiveData<MutableList<NftItem>>(mutableListOf())

    // Expose LiveData as read-only (immutable) to the external components
    val favoritesList: LiveData<MutableList<Cryptocurrency>>
        get() = _favoritesList

    val nftFavoritesList: LiveData<MutableList<NftItem>>
        get() = _nftFavoritesList

    fun addToFavorites(cryptocurrency: Cryptocurrency) {
        _favoritesList.value?.add(cryptocurrency)
        _favoritesList.postValue(_favoritesList.value) // Trigger LiveData update
    }

    fun addToNftFavorites(nftData: NftItem) {
        _nftFavoritesList.value?.add(nftData)
        _nftFavoritesList.postValue(_nftFavoritesList.value) // Trigger LiveData update
    }

    fun removeFromFavorites(cryptocurrency: Cryptocurrency) {
        _favoritesList.value?.remove(cryptocurrency)
        _favoritesList.postValue(_favoritesList.value) // Trigger LiveData update
    }

    fun removeFromNftFavorites(nftData: NftItem) {
        _nftFavoritesList.value?.remove(nftData)
        _nftFavoritesList.postValue(_nftFavoritesList.value) // Trigger LiveData update
    }
}
