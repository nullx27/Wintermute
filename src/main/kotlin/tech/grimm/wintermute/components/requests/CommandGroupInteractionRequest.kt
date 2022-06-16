package tech.grimm.wintermute.components.requests

import discord4j.core.`object`.command.ApplicationCommand
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import tech.grimm.wintermute.annotations.CommandGroup
import tech.grimm.wintermute.annotations.SubCommand
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation

class CommandGroupInteractionRequest : InteractionRequest<CommandGroup> {

    override fun create(meta: CommandGroup, klass: KClass<*>?): ImmutableApplicationCommandRequest {
        val subcommands: ArrayList<SubCommand> = arrayListOf<SubCommand>()
        klass?.members?.map {
            if (it.hasAnnotation<SubCommand>()) {
                subcommands.add(it.annotations.filterIsInstance<SubCommand>().first())
            }
        }

        if (subcommands.isEmpty()) throw Exception("CommandGroup $klass has no subcommands")

        val subcmds: ArrayList<ApplicationCommandOptionData> = ArrayList()

        subcommands.map {
            val options: ArrayList<ApplicationCommandOptionData> = ArrayList()

            for (opt in it.options) {

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

            val cmd: ApplicationCommandOptionData = ApplicationCommandOptionData
                .builder()
                .type(ApplicationCommandOption.Type.SUB_COMMAND.value)
                .name(it.name)
                .description(it.description)
                .options(options)
                .build()

            subcmds.add(cmd)
        }

        return ApplicationCommandRequest
            .builder()
            .name(meta.name.lowercase())
            .description(meta.description)
            .type(ApplicationCommand.Type.CHAT_INPUT.value)
            .addAllOptions(subcmds)
            .build()
    }
}