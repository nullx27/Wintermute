package tech.grimm.wintermute.interactions.message

import discord4j.core.event.domain.interaction.MessageInteractionEvent
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.MessageCommand
import tech.grimm.wintermute.interactions.MessageInteraction

@MessageCommand("Archive")
class ArchiveMessageInteraction: MessageInteraction {
    override fun handle(event: MessageInteractionEvent): Mono<Void> {
        // TODO: 05/06/2022 Implement archive 
        return event.reply().withContent("Message Archived");
    }
}