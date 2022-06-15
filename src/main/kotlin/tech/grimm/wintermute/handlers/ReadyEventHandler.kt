package tech.grimm.wintermute.handlers

import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.rest.RestClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tech.grimm.wintermute.components.Interactions
import tech.grimm.wintermute.utils.LoggerDelegate

@Component
class ReadyEventHandler(private val interactions: Interactions) : Handler<ReadyEvent> {

    private val logger by LoggerDelegate()

    private val guildId: Long = 261572776274427904

    override fun handle(event: ReadyEvent, client: RestClient): Mono<Void> {

        interactions.register()

        client.applicationId.block()?.let {
            client.applicationService.bulkOverwriteGuildApplicationCommand(
                it,
                guildId,
                interactions.requests
            )
                .doOnNext { interaction -> logger.info("Successfully registered interaction: ${interaction.name()}") }
                .doOnError { e -> logger.error("Failed to register commands", e) }
                .subscribe()
        }

        logger.info("Logged in as ${event.self.username}")
        return Mono.empty()
    }
}