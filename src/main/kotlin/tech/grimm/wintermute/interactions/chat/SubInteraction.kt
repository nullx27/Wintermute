package tech.grimm.wintermute.interactions.chat

import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.ChatCommand
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.interactions.ChatInteraction

@ChatCommand(
    "sub",
    "sub command test",
    [Option(
        "group",
        "test",
        ApplicationCommandOption.Type.SUB_COMMAND,
        false,
    )]
)
class SubInteraction : ChatInteraction {
    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        TODO("Not yet implemented")
    }
}