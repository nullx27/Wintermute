package tech.grimm.wintermute.components


import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import org.reflections.Reflections
import org.springframework.stereotype.Component
import tech.grimm.wintermute.annotations.ChatCommand
import tech.grimm.wintermute.annotations.CommandGroup
import tech.grimm.wintermute.annotations.MessageCommand
import tech.grimm.wintermute.components.requests.ChatCommandInteractionRequest
import tech.grimm.wintermute.components.requests.CommandGroupInteractionRequest
import tech.grimm.wintermute.components.requests.MessageCommandInteractionRequest
import kotlin.collections.set
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations

@OptIn(ExperimentalStdlibApi::class)
@Component
class Interactions {

    val requests: ArrayList<ApplicationCommandRequest> = ArrayList()
    val handlers: HashMap<String, Class<*>> = HashMap()

    private val annotationClassList: List<KClass<out Annotation>> = listOf(ChatCommand::class, MessageCommand::class, CommandGroup::class)

    fun register() {
        val reflections = Reflections("tech.grimm.wintermute.interactions")

        val interactions: MutableSet<Class<*>> = mutableSetOf()
        annotationClassList.map { interactions += reflections.getTypesAnnotatedWith(it.java) }

        interactions.map { _class ->
            val kclass = _class.kotlin

            // we need to make sure that there is exactly one command and filter out all other possible annotations
            val annotations: ArrayList<Annotation> = arrayListOf()
            annotationClassList.map { annotations.addAll(kclass.findAnnotations(it)) }

            if (annotations.count() != 1) throw Exception("$kclass can only have exactly one command annotation")

            val meta = when (annotations.first()) {
                is ChatCommand -> kclass.findAnnotation<ChatCommand>()
                is MessageCommand -> kclass.findAnnotation<MessageCommand>()
                is CommandGroup -> kclass.findAnnotation<CommandGroup>()
                else -> throw Exception("Unknown Interaction $kclass")
            }

            val interaction = createInteraction(meta, kclass)

            requests.add(interaction)
            handlers[interaction.name().lowercase()] = _class
        }
    }

    fun <T> createInteraction(annotations: T, kClass: KClass<*>): ImmutableApplicationCommandRequest =
        when (annotations) {
            is ChatCommand -> ChatCommandInteractionRequest().create(annotations)
            is MessageCommand -> MessageCommandInteractionRequest().create(annotations)
            is CommandGroup -> CommandGroupInteractionRequest().create(annotations, kClass)
            else -> throw Exception("Unknown Interaction Meta Data: $annotations")
        }
}