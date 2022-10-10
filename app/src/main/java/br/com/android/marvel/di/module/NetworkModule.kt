package br.com.android.marvel.di.module

import br.com.android.marvel.data.api.Api
import br.com.gms.data.data.api.MarvelApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun marvelApiService(): MarvelApiService {
        return Retrofit.Builder()
            .baseUrl(Api.Host.MARVEL)
            .addConverterFactory(jacksonConverterFactory())
            .client(httpClient())
            .build()
            .create(MarvelApiService::class.java)
    }

    @Provides
    @Singleton
    fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors().add(httpLoggingInterceptor())
            connectTimeout(Api.TimeOut.TIME, TimeUnit.SECONDS)
            writeTimeout(Api.TimeOut.TIME, TimeUnit.SECONDS)
            readTimeout(Api.TimeOut.TIME, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun jacksonConverterFactory(): JacksonConverterFactory {
        return JacksonConverterFactory.create()
    }
}