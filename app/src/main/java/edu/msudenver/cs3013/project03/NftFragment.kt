package edu.msudenver.cs3013.project03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NftFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NftFragment : Fragment() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.blockspan.com")
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .client(createOkHttpClient())
            .build()
    }
    private val nftApiService by lazy {
        retrofit.create(NftApiService::class.java)
    }
    private lateinit var recyclerView: RecyclerView
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    private lateinit var adapter: NftAdapter

//    private val serverResponseView: TextView by lazy {
//        view?.findViewById<TextView>(R.id.nft_server_response)!!
//    }
//    private val profileImageView: ImageView by lazy {
//        view?.findViewById<ImageView>(R.id.nftImage)!!
//    }




    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nft, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewNft)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getNftImageResponse()

    }

    private fun getNftImageResponse() {
        val call = nftApiService.searchNfts("eth-main")
        call.enqueue(object : Callback<NftData> {
            override fun onFailure(call: Call<NftData>, t: Throwable) {
                Log.e("Nft Fragment", "Failed to get search results", t)
            }

            override fun onResponse(
                call: Call<NftData>,
                response: Response<NftData>
            ) {
                if (response.isSuccessful) {
                    val listings = response.body()?.results?.toMutableList()
                    // Set up the RecyclerView adapter
                    if (listings != null) {
                        Log.d("Nft Fragment", "Got search results: $listings")
                        adapter = NftAdapter(
                            nftData = listings,
                            onAddButtonClickListener = { nftItem ->
                                // Add the NFT to favorites.
                                favoritesViewModel.addToNftFavorites(nftItem)
                                adapter.removeItem(nftItem)
                            }

                        )
                        recyclerView.adapter = adapter
                    }
                } else {
                    Log.e(
                        "CoinFragment",
                        "Failed to get cryptocurrency listings\n${response.errorBody()?.string().orEmpty()}"
                    )
                }
            }
        })
    }


    private fun createOkHttpClient(): OkHttpClient {
        val apiKey = "EFy3JzHQ0WQDpScrplwG9fZHGquhD5DV"
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header("X-API-KEY", apiKey)
            val request = requestBuilder.build()
            Log.d("Nft Fragment", request.toString())
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NftFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NftFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

}