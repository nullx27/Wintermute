package tech.grimm.wintermute.annotations

import org.springframework.stereotype.Indexed


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Indexed
annotation class Interaction()