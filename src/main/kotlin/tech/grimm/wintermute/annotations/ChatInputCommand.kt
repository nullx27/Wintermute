package tech.grimm.wintermute.annotations

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ChatInputCommand(val name: String, val description: String, val options: Array<Option> = []) {

}