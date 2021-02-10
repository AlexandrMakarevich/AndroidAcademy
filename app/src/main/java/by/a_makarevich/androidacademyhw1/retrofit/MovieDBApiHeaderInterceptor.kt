package by.a_makarevich.androidacademyhw1.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class MovieDBApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter(API_KEY_HEADER, apiKey)
            .build()
        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .build()
        return chain.proceed(request)
    }
    companion object {
        private const val API_KEY_HEADER = "api_key"
        const val apiKey = "1d52efae9f0ec74665609b2f7daace03"
    }
}
