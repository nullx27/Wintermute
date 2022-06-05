package tech.grimm.wintermute.components

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wintermute")
data class Properties(
    var discordToken: String = "",
    var youtubeToken: String = ""
)