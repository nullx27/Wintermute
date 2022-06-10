package tech.grimm.wintermute.utils

import discord4j.common.util.Snowflake
import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent

fun <T> getChatInputInteractionEventOption(event: ChatInputInteractionEvent, name: String, type: T): T {
    return event.getOption("amount")
        .flatMap { obj: ApplicationCommandInteractionOption -> obj.value }
        .map { obj: ApplicationCommandInteractionOptionValue ->
            when (type) {
                is String -> obj.asString()
                is Boolean -> obj.asBoolean()
                is Long -> obj.asLong()
                is Double -> obj.asDouble()
                is Snowflake -> obj.asSnowflake()
                else -> obj.raw
            }
        }
        .get() as T
}