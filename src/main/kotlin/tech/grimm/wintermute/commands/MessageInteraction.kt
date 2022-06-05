package tech.grimm.wintermute.commands

import discord4j.core.event.domain.interaction.MessageInteractionEvent
import reactor.core.publisher.Mono

interface MessageInteraction {
    fun handle(event: MessageInteractionEvent): Mono<Void>
}