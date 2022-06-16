package tech.grimm.wintermute.interactions

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.SubCommand
import kotlin.reflect.full.memberFunctions

abstract class GroupCommandChatInteraction : ChatInteraction {
    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        return this::class.memberFunctions.first {
            it.annotations
                .filterIsInstance<SubCommand>().any { annotation -> annotation.name == event.options.first().name }
        }
            .call(this, event) as Mono<Void>
    }
}