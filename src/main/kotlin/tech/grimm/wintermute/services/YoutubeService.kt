package tech.grimm.wintermute.services

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import org.springframework.stereotype.Service
import tech.grimm.wintermute.components.Properties

@Service
class YoutubeService(private val properties: Properties) {

    private val appName: String = "Wintermute Discord Bot";

    private fun youtube(): YouTube {
        val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return YouTube.Builder(httpTransport, GsonFactory.getDefaultInstance(), null).setApplicationName(appName)
            .build()
    }


    fun search(term: String): String? {
        val service = youtube();

        val request: YouTube.Search.List = service.search().list(listOf())
        val resposne: SearchListResponse = request
            .setKey(properties.youtubeToken)
            .setQ("test")
            .setSafeSearch("none")
            .setChannelType("any")
            .setOrder("relevance")
            .execute();

        if (resposne.items.isEmpty()) return null

        return buildYoutubeUrl(resposne.items[0].id.videoId)

    }

    private fun buildYoutubeUrl(videoId: String): String {
        return "https://www.youtube.com/watch?v=$videoId"
    }
}