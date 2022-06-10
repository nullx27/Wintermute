package tech.grimm.wintermute.interactions.chat

import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.ChatCommand
import tech.grimm.wintermute.annotations.Choice
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.interactions.ChatInteraction

@ChatCommand(
    name = "Ping",
    "Ping/Pong Command",
    options = [Option("Pong", "test", ApplicationCommandOption.Type.STRING, true, [Choice("Pong", "pong")])]
)
class PingChatInteraction : ChatInteraction {

    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        return event.reply().withEphemeral(true).withContent("Pong!")
    }
}