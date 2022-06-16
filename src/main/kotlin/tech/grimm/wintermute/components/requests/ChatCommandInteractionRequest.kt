package tech.grimm.wintermute.components.requests

import discord4j.core.`object`.command.ApplicationCommand
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import tech.grimm.wintermute.annotations.ChatCommand
import kotlin.reflect.KClass

class ChatCommandInteractionRequest : InteractionRequest<ChatCommand> {
    override fun create(meta: ChatCommand, klass: KClass<*>?): ImmutableApplicationCommandRequest {

        val options: ArrayList<ApplicationCommandOptionData> = ArrayList()

        for (opt in meta.options) {

            val choices: ArrayList<ApplicationCommandOptionChoiceData> = ArrayList()

            for (choice in opt.choices) {
                val ch = ApplicationCommandOptionChoiceData.builder()
                    .name(choice.name)
                    .value(choice.value)
                    .build()

                choices.add(ch)
            }

            val option: ApplicationCommandOptionData = ApplicationCommandOptionData.builder()
                .name(opt.name.lowercase())
                .description(opt.description)
                .type(opt.type.value)
                .required(opt.required)
                .addAllChoices(choices)
                .build()

            options.add(option)
        }

        return ApplicationCommandRequest
            .builder()
            .name(meta.name.lowercase())
            .description(meta.description)
            .type(ApplicationCommand.Type.CHAT_INPUT.value)
            .addAllOptions(options)
            .build()
    }
}