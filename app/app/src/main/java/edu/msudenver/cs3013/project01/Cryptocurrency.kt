package edu.msudenver.cs3013.project03

import android.net.eap.EapSessionConfig.EapAkaPrimeConfig

data class Cryptocurrency(
    val id: String,
    val name: String,
    val symbol: String,
    val quote: Quote
)

data class Quote(
    val USD: Price
)

data class Price(
    val price: Double
)
data class ListingResponse(
    val data: List<Cryptocurrency>
)
