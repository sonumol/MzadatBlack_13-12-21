package app.platinummzadat.qa.networking;

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.networking.interceptor.HeaderInterceptor
import app.platinummzadat.qa.networking.interceptor.LoggingInterceptor
import net.idik.lib.cipher.so.CipherClient
import okhttp3.*
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object HTTPClient {

    val coroutineRestfulAPI: CoroutineRestfulAPI by lazy {
        initializeRetrofit<CoroutineRestfulAPI>(
                CoroutineCallAdapterFactory()
        )
    }
    private inline fun <reified T> initializeRetrofit(retrofitFactory: CallAdapter.Factory): T {



        val httpClient = OkHttpClient
            .Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
           .addInterceptor(HeaderInterceptor())
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(
                                MApp.applicationContext()
                        )!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 15).build() //15 sec if network available
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build() //7days
                chain.proceed(request)
            }
            .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
          //  .addInterceptor(LoggingInterceptor())
            .build() // your own http client




        val retrofit = Retrofit.Builder()
            .baseUrl(CipherClient.serverApi())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(retrofitFactory)
            .client(httpClient)
            .build()



        return retrofit.create(T::class.java)
    }

    private val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
            cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 10000)
                .build()
        } else {
            originalResponse
        }
    }

    private val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = Interceptor { chain ->
        var request = chain.request()
        if (!hasNetwork(
                        MApp.applicationContext()
                )!!) {
            request = request.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached")
                .build()
        }
        chain.proceed(request)
    }
    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}