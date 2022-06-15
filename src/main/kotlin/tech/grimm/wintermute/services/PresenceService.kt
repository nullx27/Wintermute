package tech.grimm.wintermute.services

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.presence.Activity
import discord4j.core.`object`.presence.ClientActivity
import discord4j.core.`object`.presence.ClientPresence
import org.springframework.stereotype.Service

@Service
class PresenceService(val client: GatewayDiscordClient) {

    fun update(type: Activity.Type, name: String, url: String? = null) {
        val presence = ClientPresence.online(
            ClientActivity.of(type, name, url)
        )

        client.updatePresence(presence).subscribe()
    }
}