package tech.grimm.wintermute.handlers

import discord4j.core.event.domain.interaction.MessageInteractionEvent
import discord4j.rest.RestClient
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tech.grimm.wintermute.commands.MessageInteraction
import tech.grimm.wintermute.components.CommandRegistry

@Component
class MessageInteractionHandler(private val commandRegistry: CommandRegistry, private val ctx: ApplicationContext) :
    Handler<MessageInteractionEvent> {

    override fun handle(event: MessageInteractionEvent, client: RestClient): Mono<Void> {

        val cmd =
            ctx.getBeansOfType(commandRegistry.commandInstances[event.commandName.lowercase()]).values.firstOrNull() as MessageInteraction;
        return cmd.handle(event) ?: throw Exception("Can't find ${event.commandName}")
    }
}