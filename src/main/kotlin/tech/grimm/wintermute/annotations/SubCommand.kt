package tech.grimm.wintermute.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SubCommand(val name: String, val description: String, val options: Array<Option> = [])