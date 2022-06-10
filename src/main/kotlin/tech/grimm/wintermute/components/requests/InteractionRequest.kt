package tech.grimm.wintermute.components.requests

import discord4j.discordjson.json.ImmutableApplicationCommandRequest

interface InteractionRequest<T: Annotation> {
   fun create(meta: T): ImmutableApplicationCommandRequest
}