package tech.grimm.wintermute.interactions

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

interface ChatInteraction {
    fun handle(event: ChatInputInteractionEvent): Mono<Void>
}