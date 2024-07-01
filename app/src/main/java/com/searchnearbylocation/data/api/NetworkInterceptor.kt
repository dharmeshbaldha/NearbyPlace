package com.searchnearbylocation.data.api

import com.searchnearbylocation.utils.Constants.AUTHORIZATION_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", AUTHORIZATION_KEY)
        request.addHeader("accept", "application/json")
        return chain.proceed(request.build())
    }
}