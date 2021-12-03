package app.platinummzadat.qa.networking.interceptor



import android.content.Context
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                .addHeader("deviceplatform","android")
                .addHeader("x-auth-token", "")
                .addHeader("Cache-Control", "max-age=640000")
                .addHeader("Dev", "mbacat1010@gmail.com")
                .addHeader("DevProfile", "https://wazimbadsha.github.io/prof/")
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                .build()
        return chain.proceed(request)
    }
}