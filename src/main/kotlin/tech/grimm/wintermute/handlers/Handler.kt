package tech.grimm.wintermute.handlers

import discord4j.core.event.domain.Event
import discord4j.rest.RestClient
import reactor.core.publisher.Mono

interface Handler<T: Event> {
    fun handle(event: T, client: RestClient): Mono<Void>
}