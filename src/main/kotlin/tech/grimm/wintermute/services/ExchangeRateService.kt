package tech.grimm.wintermute.services

import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import org.springframework.stereotype.Service
import tech.grimm.wintermute.services.models.ExchangeRate

@Service
class ExchangeRateService {

    private final val exchangeApiUrl = "https://api.exchangerate.host/latest";

    fun getExchangeRate(amount: Double, base: String, target: String): ExchangeRate {

        val payload = listOf(
            "base" to base.uppercase(),
            "symbols" to target.uppercase(),
            "amount" to amount
        );

        // TODO: 29/05/2022 error handling would be nice
        val (request, response, result) = exchangeApiUrl.httpGet(payload).responseObject<ExchangeRate>()
        return result.get()
    }
}