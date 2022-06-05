package tech.grimm.wintermute.components

import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.presence.ClientActivity
import discord4j.core.`object`.presence.ClientPresence
import discord4j.core.event.domain.Event
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.gateway.intent.IntentSet
import discord4j.rest.RestClient
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tech.grimm.wintermute.handlers.ChatInputInteractionHandler
import tech.grimm.wintermute.handlers.Handler
import tech.grimm.wintermute.handlers.ReadyEventHandler
import tech.grimm.wintermute.utils.LoggerDelegate

@Component
class Discord(
    private val chatInputInteractionHandler: ChatInputInteractionHandler,
    private val readyEventHandler: ReadyEventHandler
) {

    private val logger by LoggerDelegate()

    @Bean
    fun gatewayDiscordClient(config: Properties): GatewayDiscordClient? {
        val client = DiscordClientBuilder.create(config.discordToken).build()
            .gateway()
            .setEnabledIntents(IntentSet.all())
            .setAwaitConnections(false)
            .setInitialPresence {
                ClientPresence.online(
                    ClientActivity.watching("you!")
                )
            }
            .login()
            .doOnError { e -> logger.error("Failed to log in: ", e) }
            .doOnSuccess { logger.info("Successfully logged in!") }
            .block()

        client!! // TODO: 26/05/2022 this needs to me made more idiomatic

        registerHandler(ReadyEvent::class.java, client, readyEventHandler)
        registerHandler(ChatInputInteractionEvent::class.java, client, chatInputInteractionHandler)

        client.onDisconnect().block()

        return client
    }

    private fun <T : Event> registerHandler(event: Class<T>, client: GatewayDiscordClient, handler: Handler<T>) {
        client.on(event)
            .doOnNext { e -> logger.info("Handled $e.") }
            .flatMap { e -> handler.handle(e, client.restClient) }
            .onErrorResume { err -> Mono.fromRunnable { logger.error("$err") } }
            .subscribe()
    }

    @Bean
    fun discordRestClient(client: GatewayDiscordClient): RestClient {
        return client.restClient
    }
}