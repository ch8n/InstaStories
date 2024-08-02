package dev.ch8n.instastories.ui.injector

import android.content.Context

object AppInjector {

    private var _appContext: Context? = null
    val appContext get() = requireNotNull(_appContext)

    fun setAppContext(context: Context) {
        _appContext = context.applicationContext
    }

    fun release() {
        _appContext = null
    }
}