package tech.grimm.wintermute.commands

import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.ChatInputCommand
import tech.grimm.wintermute.annotations.Choice
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.utils.LoggerDelegate

@ChatInputCommand(
    name = "Ping",
    "Ping/Pong Command",
    options = [Option("Pong", "test", ApplicationCommandOption.Type.STRING, true, [Choice("Pong", "pong")])]
)
class PingCommand : Command {
    private val logger by LoggerDelegate()


    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        logger.info("HIT")
        return event.reply().withEphemeral(true).withContent("Pong!")
    }
}