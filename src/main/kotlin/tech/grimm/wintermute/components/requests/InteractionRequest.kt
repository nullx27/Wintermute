package tech.grimm.wintermute.components.requests

import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import kotlin.reflect.KClass

interface InteractionRequest<T: Annotation> {
   fun create(meta: T, klass: KClass<*>? = null): ImmutableApplicationCommandRequest
}