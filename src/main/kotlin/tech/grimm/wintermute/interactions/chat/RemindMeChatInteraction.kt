package tech.grimm.wintermute.interactions.chat

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.command.ApplicationCommandOption
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.Choice
import tech.grimm.wintermute.annotations.CommandGroup
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.annotations.SubCommand
import tech.grimm.wintermute.interactions.GroupCommandChatInteraction

@CommandGroup("RemindMe", "Remind Me later")
class RemindMeChatInteraction : GroupCommandChatInteraction() {

    @SubCommand(
        "new", "Add a new reminder", [
            Option("Reminder", "What you want to be reminded of", ApplicationCommandOption.Type.STRING, true),
            Option("time", "Time Amount", ApplicationCommandOption.Type.NUMBER, true),
            Option(
                "unit", "Time Unit", ApplicationCommandOption.Type.STRING, true, [
                    Choice("Minutes", "minutes"),
                    Choice("Hours", "hours"),
                    Choice("Days", "days"),
                    Choice("Weeks", "weeks"),
                ]
            )
        ]
    )
    fun add(event: ChatInputInteractionEvent): Mono<Void> {
        return event.reply().withContent("test")
    }

    @SubCommand("list", "Show my reminders")
    fun show(event: ChatInputInteractionEvent): Mono<Void> {
        return Mono.empty()
    }

    @SubCommand(
        "delete",
        "Delete a reminder",
        [Option(
            "id",
            "Number of your reminder, see remindme list",
            ApplicationCommandOption.Type.NUMBER,
            true
        )]
    )
    fun delete(event: ChatInputInteractionEvent): Mono<Void> {
        return Mono.empty()
    }
}