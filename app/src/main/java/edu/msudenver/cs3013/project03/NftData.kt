package edu.msudenver.cs3013.project03

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class NftData(
    val results: List<NftItem>
    // other properties if any
)
data class NftItem(
    val cached_images: Images?,
    val recent_price: Prices?
    // other properties if any
)
data class Images(
    val tiny_100_100: String
    // other properties if any
)
data class Prices(
    val price_usd: Double?
    // other properties if any
)

//
//"token_type": "erc721",
//"contract_address": "0x2f329de74f67e2c1c5d2a6487ae50c99873d8589",
//"id": 5336,