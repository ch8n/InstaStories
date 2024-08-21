package dev.ch8n.instastories.server

import dev.ch8n.instastories.FakeStories
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object EmbeddedServer : CoroutineScope {
    private var server: NettyApplicationEngine? = null
    private var serverJob: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun startServer() {
        serverJob?.cancel()
        serverJob = launch {
            embeddedServer(Netty, port = 8080, module = Application::module)
                .also { it.start() }
                .also { server = it }
        }
    }

    fun stopServer() {
        server?.stop(0, 0)
        serverJob?.cancel()
    }
}


fun Application.module() {
    install(ContentNegotiation) {
        gson()
    }

    routing {
        get("/chetan/api/stories") {
            call.respond(FakeStories.getStories())
        }
    }
}