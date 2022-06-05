package tech.grimm.wintermute.annotations

import discord4j.core.`object`.command.ApplicationCommandOption

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Option(val name: String, val description: String, val type: ApplicationCommandOption.Type, val required: Boolean, val choices: Array<Choice> = []) {
}