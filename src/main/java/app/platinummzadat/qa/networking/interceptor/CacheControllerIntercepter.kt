
package app.platinummzadat.qa.networking.interceptor
import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.connection.NetworkUtil
import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheControllerIntercepter : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtil.isNetworkConnected(MApp.applicationContext())) {
            val cacheControl = CacheControl.Builder()
                    .maxStale(10, TimeUnit.SECONDS)
                    .build()
            request = request.newBuilder().cacheControl(cacheControl).build()
        }

        return chain.proceed(request)
    }
}
