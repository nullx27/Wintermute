package tech.grimm.wintermute.handlers

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.rest.RestClient
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tech.grimm.wintermute.commands.ChatCommand
import tech.grimm.wintermute.components.CommandRegistry

@Component
class ChatInputInteractionHandler(private val commandRegistry: CommandRegistry, private val ctx: ApplicationContext) :
    Handler<ChatInputInteractionEvent> {

    override fun handle(event: ChatInputInteractionEvent, client: RestClient): Mono<Void> {
        val cmd =
            ctx.getBeansOfType(commandRegistry.commandInstances[event.commandName]).values.firstOrNull() as ChatCommand
        return cmd.handle(event)
    }

}