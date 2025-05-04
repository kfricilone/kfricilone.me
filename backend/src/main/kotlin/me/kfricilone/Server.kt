/*
 * Copyright (c)  Kyle Fricilone (https://kfricilone.me)
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package me.kfricilone

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.EngineMain
import io.ktor.server.http.content.resolveResource
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.conditionalheaders.ConditionalHeaders
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.respondTemplate
import io.ktor.server.webjars.Webjars
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.nio.charset.StandardCharsets

private val repos: List<String> =
    listOf(
        "kfricilone/pasty",
        "kfricilone/kfricilone.me",
        "kfricilone/kard",
        "kfricilone/kached",
        "kfricilone/OpenRS",
        "service-rs/spring-rs",
    )

public fun main(args: Array<String>): Unit = EngineMain.main(args)

public fun Application.module() {
    install(DefaultHeaders)
    install(ConditionalHeaders)
    install(Webjars)
    install(CallLogging) {
        disableDefaultColors()
    }
    install(Thymeleaf) {
        setTemplateResolver(
            ClassLoaderTemplateResolver().apply {
                prefix = "templates/"
                suffix = ".html"
                characterEncoding = StandardCharsets.UTF_8.name()
            },
        )
    }

    routing {
        get("/") {
            call.respondTemplate(template = "home", model = mapOf("repos" to repos))
        }

        get("/gpg") {
            call.respond(call.resolveResource("gpg.txt", "static")!!)
        }
    }
}
