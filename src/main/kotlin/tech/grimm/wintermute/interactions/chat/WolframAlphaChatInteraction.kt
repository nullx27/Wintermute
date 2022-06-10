package tech.grimm.wintermute.interactions.chat

import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.EmbedCreateSpec
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.ChatCommand
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.interactions.ChatInteraction
import tech.grimm.wintermute.services.WolframAlphaService

@ChatCommand(
    "wolframalpha",
    "Query WolframAlpha",
    [Option("input", "Calculation Input", ApplicationCommandOption.Type.STRING, true)]
)
class WolframAlphaChatInteraction(private val wolframAlphaService: WolframAlphaService) : ChatInteraction {
    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        val input = event.getOption("input")
            .flatMap { obj: ApplicationCommandInteractionOption -> obj.value }
            .map { obj: ApplicationCommandInteractionOptionValue -> obj.asString() }
            .get()

        val result = wolframAlphaService.execute(input)
        if (!result.queryresult.success) return event.reply().withContent("No Result")

        val embed = EmbedCreateSpec.builder()
            .author("Wolfram|Alpha", "https://www.wolframalpha.com/", "https://i.imgur.com/YVWvjlM.png")

        for (pod in result.queryresult.pods) {
            when (pod.id) {
                "Input", "Result" -> embed.addField(
                    pod.title,
                    pod.subpods[0].plaintext,
                    false
                )
                "Plot" -> embed.image(pod.subpods[0].img.src)
            }
        }

        return event.reply().withEmbeds(embed.build())
    }

}