package dev.ch8n.instastories.data.local.injector

import android.content.Context
import dev.ch8n.instastories.data.local.database.AppDatabase

object LocalServiceProvider {

    private var applicationContext: Context? = null
    fun initAppContext(context: Context) {
        applicationContext = context.applicationContext
    }

    private val appDatabase by lazy {
        AppDatabase.getDatabase(requireNotNull(applicationContext))
    }

    val viewStoriesDAO by lazy { appDatabase.viewedStoryDao() }

}