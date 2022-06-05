package tech.grimm.wintermute.services

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import org.springframework.stereotype.Service
import tech.grimm.wintermute.components.Properties
import tech.grimm.wintermute.services.models.WolframAlphaResponse

@Service
class WolframAlphaService(private val properties: Properties) {

    private val wolframAlphaUrl: String = "https://api.wolframalpha.com/v2/query"

    fun execute(term: String): WolframAlphaResponse {

        val payload = listOf(
            "input" to term,
            "output" to "json",
            "appid" to properties.wolframAlphaKey
        );

        val (request, response, result) = wolframAlphaUrl.httpGet(payload).also { request: Request ->   }.responseObject<WolframAlphaResponse>()

        return result.get()
    }
}