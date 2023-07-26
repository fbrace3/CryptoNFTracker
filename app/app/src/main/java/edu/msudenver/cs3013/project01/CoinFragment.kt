package edu.msudenver.cs3013.project03
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class CoinFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val apiKey = "1bfa7bc2-b0f4-453c-9196-e1cf8faf235a"

    // Use the shared ViewModel
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()

    private lateinit var adapter: CryptocurrencyAdapter


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com")
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .client(createOkHttpClient())
            .build()
    }

    private val coinApiService by lazy {
        retrofit.create(CoinApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getCoinImageResponse()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header("X-CMC_PRO_API_KEY", apiKey)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun getCoinImageResponse() {
        val call = coinApiService.getCryptocurrencyListings()
        call.enqueue(object : Callback<ListingResponse> {
            override fun onFailure(call: Call<ListingResponse>, t: Throwable) {
                Log.e("CoinFragment", "Failed to get cryptocurrency listings", t)
            }

            override fun onResponse(
                call: Call<ListingResponse>,
                response: Response<ListingResponse>
            ) {
                if (response.isSuccessful) {
                    val listings = response.body()?.data
                    // Set up the RecyclerView adapter
                    if (listings != null) {
                        adapter = CryptocurrencyAdapter(listings.toMutableList(), { selectedCryptocurrency ->
                            favoritesViewModel.addToFavorites(selectedCryptocurrency)
                            adapter.removeItem(selectedCryptocurrency)
                        }, true)
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


    private fun getMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}
