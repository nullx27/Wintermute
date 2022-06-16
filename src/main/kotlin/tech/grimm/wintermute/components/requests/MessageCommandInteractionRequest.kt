package tech.grimm.wintermute.components.requests

import discord4j.core.`object`.command.ApplicationCommand
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import tech.grimm.wintermute.annotations.MessageCommand
import kotlin.reflect.KClass

class MessageCommandInteractionRequest : InteractionRequest<MessageCommand> {
    override fun create(meta: MessageCommand, klass: KClass<*>?): ImmutableApplicationCommandRequest {
        val request: ImmutableApplicationCommandRequest.Builder = ApplicationCommandRequest.builder()
        return  request.name(meta.name).type(ApplicationCommand.Type.MESSAGE.value).build()
    }
}