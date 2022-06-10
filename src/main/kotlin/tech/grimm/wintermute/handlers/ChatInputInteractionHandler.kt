package tech.grimm.wintermute.handlers

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.rest.RestClient
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tech.grimm.wintermute.components.Interactions
import tech.grimm.wintermute.interactions.ChatInteraction

@Component
class ChatInputInteractionHandler(private val interactions: Interactions, private val ctx: ApplicationContext) :
    Handler<ChatInputInteractionEvent> {

    override fun handle(event: ChatInputInteractionEvent, client: RestClient): Mono<Void> {
        val cmd = ctx.getBeansOfType(interactions.handlers[event.commandName]).values.first() as ChatInteraction
        return cmd.handle(event)
    }

}