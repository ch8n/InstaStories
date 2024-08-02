package dev.ch8n.instastories.data.remote.injector

import dev.ch8n.instastories.ui.injector.AppInjector
import java.io.File
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.CacheControl
import java.util.concurrent.TimeUnit

object OkHttpClientProvider {

    private val httpCache by lazy {
        val cacheSize = 10 * 1024 * 1024
        val appContext = AppInjector.appContext
        val cacheDir = File(appContext.cacheDir, "http_cache")
        Cache(cacheDir, cacheSize.toLong())
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val cacheInterceptor by lazy {
        Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(0, TimeUnit.SECONDS)
                .maxStale(1, TimeUnit.MINUTES)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }


    val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cache(httpCache)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(cacheInterceptor)
            .build()
    }
}