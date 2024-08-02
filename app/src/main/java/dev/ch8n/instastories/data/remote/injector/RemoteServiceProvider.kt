package dev.ch8n.instastories.data.remote.injector

import dev.ch8n.instastories.data.remote.StoriesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteServiceProvider {

    private const val BASE_URL = "https://api.example.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClientProvider.httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val storiesService: StoriesService by lazy {
        retrofit.create(StoriesService::class.java)
    }
}