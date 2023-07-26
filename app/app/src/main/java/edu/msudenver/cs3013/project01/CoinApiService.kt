package edu.msudenver.cs3013.project03

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApiService {

    @GET("/v1/cryptocurrency/listings/latest")
    fun getCryptocurrencyListings(): Call<ListingResponse>

}
