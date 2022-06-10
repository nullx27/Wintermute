package tech.grimm.wintermute.components


import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import org.reflections.Reflections
import org.springframework.stereotype.Component
import tech.grimm.wintermute.annotations.ChatCommand
import tech.grimm.wintermute.annotations.MessageCommand
import tech.grimm.wintermute.components.requests.ChatCommandInteractionRequest
import tech.grimm.wintermute.components.requests.MessageCommandInteractionRequest
import kotlin.collections.set
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

@Component
class Interactions {

    val requests: ArrayList<ApplicationCommandRequest> = ArrayList();
    val handlers: HashMap<String, Class<*>> = HashMap();

    private val annotations: List<KClass<out Annotation>> = listOf(ChatCommand::class, MessageCommand::class)

    fun register() {
        val reflections = Reflections("tech.grimm.wintermute.interactions")

        val interactions: MutableSet<Class<*>> = mutableSetOf()
        annotations.map { interactions += reflections.getTypesAnnotatedWith(it.java) }

        interactions.map { _class ->
            val className = Class.forName(_class.name).kotlin;
            val meta =
                when (className.annotations.first()) { // TODO: 10/06/2022 this needs to be filtered to only return the first match that is contained in annotations 
                    is ChatCommand -> className.findAnnotation<ChatCommand>()
                    is MessageCommand -> className.findAnnotation<MessageCommand>()
                    else -> throw Exception("Unknown Interaction $className")
                }

            val interaction = createInteraction(meta)

            requests.add(interaction)
            handlers[interaction.name().lowercase()] = _class
        }
    }

    fun <T> createInteraction(annotations: T): ImmutableApplicationCommandRequest {
        return when (annotations) {
            is ChatCommand -> ChatCommandInteractionRequest().create(annotations)
            is MessageCommand -> MessageCommandInteractionRequest().create(annotations)
            else -> throw Exception("Unknown Interaction Meta Data: $annotations")
        }
    }
}