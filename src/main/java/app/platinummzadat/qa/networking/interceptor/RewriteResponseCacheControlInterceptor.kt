package app.platinummzadat.qa.networking.interceptor
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class RewriteResponseCacheControlInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val maxStale = 60 * 60 * 24
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder().header("Cache-Control", "public, max-age=120, max-stale=$maxStale").build()
    }
}