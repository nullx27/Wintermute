package tech.grimm.wintermute.components

import discord4j.core.`object`.command.ApplicationCommand
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import org.reflections.Reflections
import org.springframework.stereotype.Component
import tech.grimm.wintermute.annotations.ChatInputCommand
import tech.grimm.wintermute.annotations.MessageCommand
import kotlin.reflect.full.findAnnotation

@Component
class CommandRegistry {

    val commandInstances: HashMap<String, Class<*>> = HashMap<String, Class<*>>();
    val commandRequests: ArrayList<ApplicationCommandRequest> = ArrayList();

    fun loadCommands() {
        val reflections = Reflections("tech.grimm.wintermute.commands")

        val chatInputCommands: Set<Class<*>> = reflections.getTypesAnnotatedWith(ChatInputCommand::class.java)
        val messageInteractions: Set<Class<*>> = reflections.getTypesAnnotatedWith(MessageCommand::class.java)

        chatInputCommands.map {
            val kclass = Class.forName(it.name).kotlin;
            val annotations = kclass.findAnnotation<ChatInputCommand>() ?: throw Exception("$it can't read annotations")

            buildInteractions<ChatInputCommand>(annotations)
            commandInstances[annotations.name.sanitize()] = Class.forName(it.name)
        }

        messageInteractions.map {
            val kclass = Class.forName(it.name).kotlin;
            val annotations = kclass.findAnnotation<MessageCommand>() ?: throw Exception("$it can't read annotations")

            buildInteractions<MessageCommand>(annotations)
            commandInstances[annotations.name.sanitize()] = Class.forName(it.name)
        }
    }

    fun <T> buildInteractions(annotations: T) {
        val request: ImmutableApplicationCommandRequest.Builder = ApplicationCommandRequest.builder()

        when (annotations) {
            is ChatInputCommand -> {
                val options: ArrayList<ApplicationCommandOptionData> = ArrayList()

                for (opt in annotations.options) {

                    val choices: ArrayList<ApplicationCommandOptionChoiceData> = ArrayList()

                    for (choice in opt.choices) {
                        val ch = ApplicationCommandOptionChoiceData.builder()
                            .name(choice.name)
                            .value(choice.value)
                            .build()

                        choices.add(ch);
                    }

                    val option: ApplicationCommandOptionData = ApplicationCommandOptionData.builder()
                        .name(opt.name.sanitize())
                        .description(opt.description)
                        .type(opt.type.value)
                        .required(opt.required)
                        .addAllChoices(choices)
                        .build()

                    options.add(option);
                }

                request.name(annotations.name.sanitize())
                    .description(annotations.description)
                    .type(ApplicationCommand.Type.CHAT_INPUT.value)
                    .addAllOptions(options)
            }

            is MessageCommand -> {
                request.name(annotations.name)
                    .type(ApplicationCommand.Type.MESSAGE.value)
            }
        }

        commandRequests.add(request.build())
    }

}

private fun String.sanitize(): String {
    return this.lowercase() //todo: other sanitizing that the api needs
}