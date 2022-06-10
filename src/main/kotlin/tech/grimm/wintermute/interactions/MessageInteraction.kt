package tech.grimm.wintermute.interactions

import discord4j.core.event.domain.interaction.MessageInteractionEvent
import reactor.core.publisher.Mono

interface MessageInteraction {
    fun handle(event: MessageInteractionEvent): Mono<Void>
}