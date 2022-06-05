package tech.grimm.wintermute.commands

import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.ChatInputCommand
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.services.YoutubeService

@ChatInputCommand(
    "youtube",
    "Serach for youtube video",
    [Option("search", "Search Term", ApplicationCommandOption.Type.STRING, true)]
)
class YoutubeCommand(private val youtubeService: YoutubeService) : Command {
    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {

        val search = event.getOption("search")
            .flatMap { obj: ApplicationCommandInteractionOption -> obj.value }
            .map { obj: ApplicationCommandInteractionOptionValue -> obj.asString() }
            .get()

        val url =
            youtubeService.search(search) ?: return event.reply().withContent("No Video found for search term $search")

        return event.reply().withContent(url)
    }
}