package tech.grimm.wintermute.commands

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

interface ChatInputCommand {
    fun handle(event: ChatInputInteractionEvent): Mono<Void>
}