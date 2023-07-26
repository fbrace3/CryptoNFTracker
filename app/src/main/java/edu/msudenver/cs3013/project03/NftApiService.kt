package edu.msudenver.cs3013.project03

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NftApiService {
    @GET("/v1/nfts")
        fun searchNfts(@Query("chain") chain: String) : Call<NftData>

    }
