package tech.grimm.wintermute.components

import discord4j.core.`object`.command.ApplicationCommand
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import org.reflections.Reflections
import org.springframework.stereotype.Component
import tech.grimm.wintermute.annotations.ChatInputCommand
import kotlin.reflect.full.findAnnotation

@Component
class CommandRegistry {

    var loaded: Boolean = false;

    val commandInstances: HashMap<String, Class<*>> = HashMap<String, Class<*>>();
    val commandRequests: ArrayList<ApplicationCommandRequest> = ArrayList();

    fun loadCommands() {
        val reflections = Reflections("tech.grimm.wintermute.commands")

        val chatInputCommands: Set<Class<*>> = reflections.getTypesAnnotatedWith(ChatInputCommand::class.java)

        for (command in chatInputCommands) {

            val kclass = Class.forName(command.name).kotlin;
            val annotations =
                kclass.findAnnotation<ChatInputCommand>() ?: throw Exception("$command can't read annotations")

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

            val request: ApplicationCommandRequest = ApplicationCommandRequest.builder()
                .name(annotations.name.sanitize())
                .description(annotations.description)
                .type(ApplicationCommand.Type.CHAT_INPUT.value)
                .addAllOptions(options)
                .build()

            commandRequests.add(request)
            commandInstances[annotations.name.sanitize()] = Class.forName(command.name)
            loaded = true;
        }
    }

}

private fun String.sanitize(): String {
    return this.lowercase() //todo: other sanitizing that the api needs
}