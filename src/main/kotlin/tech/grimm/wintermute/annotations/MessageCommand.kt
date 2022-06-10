package tech.grimm.wintermute.annotations

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Interaction
@Component
annotation class MessageCommand(val name: String)