package dev.ch8n.instastories.server

import dev.ch8n.instastories.FakeStories
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.stop
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
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