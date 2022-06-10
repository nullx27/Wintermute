package tech.grimm.wintermute.handlers

import discord4j.core.event.domain.interaction.MessageInteractionEvent
import discord4j.rest.RestClient
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tech.grimm.wintermute.components.Interactions
import tech.grimm.wintermute.interactions.MessageInteraction

@Component
class MessageInteractionHandler(private val interactions: Interactions, private val ctx: ApplicationContext) :
    Handler<MessageInteractionEvent> {

    override fun handle(event: MessageInteractionEvent, client: RestClient): Mono<Void> {

        val cmd =
            ctx.getBeansOfType(interactions.handlers[event.commandName.lowercase()]).values.first() as MessageInteraction;
        return cmd.handle(event)
    }
}