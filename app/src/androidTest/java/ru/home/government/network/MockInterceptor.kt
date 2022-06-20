package ru.home.government.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class MockInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
        return chain.proceed(chain.request())
    }
}