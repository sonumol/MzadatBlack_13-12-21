package app.platinummzadat.qa.networking.interceptor

import app.platinummzadat.qa.MApp
import app.platinummzadat.qa.connection.NetworkUtil
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RewriteRequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val maxStale = 60 * 60 * 24 * 5
        val request: Request
        request = if (NetworkUtil.isNetworkConnected(MApp.applicationContext())) {
            chain.request()
        } else {
            chain.request().newBuilder().header("Cache-Control", "max-stale=$maxStale").build()
        }
        return chain.proceed(request)
    }
}