package tech.grimm.wintermute.commands.chatInputCommands

import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.EmbedCreateSpec
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.ChatInputCommand
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.services.WolframAlphaService

@ChatInputCommand(
    "wolframalpha",
    "Query WolframAlpha",
    [Option("term", "Calculation", ApplicationCommandOption.Type.STRING, true)]
)
class WolframAlphaChatInputCommand(private val wolframAlphaService: WolframAlphaService) :
    tech.grimm.wintermute.commands.ChatInputCommand {
    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        val term = event.getOption("term")
            .flatMap { obj: ApplicationCommandInteractionOption -> obj.value }
            .map { obj: ApplicationCommandInteractionOptionValue -> obj.asString() }
            .get()

        val result = wolframAlphaService.execute(term)
        if (result.queryresult?.success == false) return event.reply().withContent("No Result")

        val embed = EmbedCreateSpec.builder()
            .author("Wolfram|Alpha", "https://www.wolframalpha.com/", "https://i.imgur.com/YVWvjlM.png")

        for (pod in result.queryresult?.pods!!) {
            when (pod.id) {
                "Input", "Result" -> embed.addField(
                    pod.title.toString(),
                    pod.subpods?.get(0)?.plaintext.toString(),
                    false
                )
                "Plot" -> embed.image(pod.subpods?.get(0)?.img?.src.toString())
            }
        }

        return event.reply().withEmbeds(embed.build());
    }

}