package dev.ch8n.instastories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import dev.ch8n.instastories.server.EmbeddedServer
import dev.ch8n.instastories.ui.features.AppNavigation
import dev.ch8n.instastories.ui.injector.AppInjector
import dev.ch8n.instastories.ui.theme.InstaStoriesTheme

class MainActivity : ComponentActivity(), ImageLoaderFactory {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppInjector.setAppContext(applicationContext)
        EmbeddedServer.startServer()
        enableEdgeToEdge()
        setContent {
            InstaStoriesTheme {
                Surface(
                    modifier = Modifier.safeContentPadding()
                ) {
                    AppNavigation(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    override fun onDestroy() {
        EmbeddedServer.stopServer()
        super.onDestroy()
        AppInjector.release()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.20)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(5 * 1024 * 1024)
                    .build()
            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }
}


