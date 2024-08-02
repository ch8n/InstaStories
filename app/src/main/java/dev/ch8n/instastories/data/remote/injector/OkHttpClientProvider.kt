package dev.ch8n.instastories.data.remote.injector

import okhttp3.OkHttpClient

object OkHttpClientProvider {
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }
}