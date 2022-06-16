package tech.grimm.wintermute.components

import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.presence.ClientActivity
import discord4j.core.`object`.presence.ClientPresence
import discord4j.core.event.domain.Event
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.event.domain.interaction.MessageInteractionEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.gateway.intent.IntentSet
import discord4j.rest.RestClient
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import tech.grimm.wintermute.handlers.ChatInputInteractionHandler
import tech.grimm.wintermute.handlers.Handler
import tech.grimm.wintermute.handlers.MessageInteractionHandler
import tech.grimm.wintermute.handlers.ReadyEventHandler
import tech.grimm.wintermute.utils.LoggerDelegate

@Component
class Discord(
    private val chatInputInteractionHandler: ChatInputInteractionHandler,
    private val readyEventHandler: ReadyEventHandler,
    private val messageInteractionHandler: MessageInteractionHandler
) {

    private val logger by LoggerDelegate()

    @Bean
    fun gatewayDiscordClient(config: Properties): GatewayDiscordClient? =
        DiscordClientBuilder.create(config.discordToken).build()
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
            .doOnSuccess {
                logger.info("Successfully logged in!")

                registerHandler(ReadyEvent::class.java, it, readyEventHandler)
                registerHandler(ChatInputInteractionEvent::class.java, it, chatInputInteractionHandler)
                registerHandler(MessageInteractionEvent::class.java, it, messageInteractionHandler)
            }
            .block()

    private fun <T : Event> registerHandler(event: Class<T>, client: GatewayDiscordClient, handler: Handler<T>) =
        client.on(event)
            .doOnNext { e -> logger.info("Handled ${e::class.java}.") }
            .flatMap { e -> handler.handle(e, client.restClient) }
            .onErrorContinue { t, _ -> t.printStackTrace() }
            .subscribe()


    @Bean
    fun discordRestClient(client: GatewayDiscordClient): RestClient = client.restClient

}