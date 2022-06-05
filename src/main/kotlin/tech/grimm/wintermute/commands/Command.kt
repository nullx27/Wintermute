package tech.grimm.wintermute.commands

import reactor.core.publisher.Mono
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

interface Command {

    @AliasFor(annotation = Component::class)
    fun value(): String? = ""

    fun handle(event: ChatInputInteractionEvent) : Mono<Void>
}