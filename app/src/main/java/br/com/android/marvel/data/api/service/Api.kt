package br.com.android.marvel.data.api.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    private val BASE_URL: String = "https://private-b34167-rvmarvel.apiary-mock.com/"

    /**
     * Method to create a generic repository class
     */
    fun <T> createService(serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    /**
     * Method to create a generic repository class, where it will be allowed to pass an authentication token to be injected into the header
     */
    fun <T> createService(serviceClass: Class<T>, accessToken: String): T {

        val httpClient = OkHttpClient.Builder()
        val authenticationInterceptor = AuthenticationInterceptor(accessToken)

        if (!httpClient.interceptors().contains(authenticationInterceptor)) {
            httpClient.interceptors().add(authenticationInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(serviceClass)
    }

    /**
     * Authentication interceptor for possible routes that require authentication
     */
    private class AuthenticationInterceptor(private val accessToken: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder: Request.Builder = chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .header("api-key", accessToken)
            return chain.proceed(builder.build())
        }
    }
}