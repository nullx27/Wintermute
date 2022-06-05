package tech.grimm.wintermute.commands

import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import reactor.core.publisher.Mono
import tech.grimm.wintermute.annotations.ChatInputCommand
import tech.grimm.wintermute.annotations.Choice
import tech.grimm.wintermute.annotations.Option
import tech.grimm.wintermute.services.ExchangeRateService
import tech.grimm.wintermute.utils.Currency
import java.time.Instant


@ChatInputCommand("exchange", "Exchange Rates for Currencies", [
    Option("amount", "Currency Amount", ApplicationCommandOption.Type.NUMBER, true),
    Option("from", "Base Currency", ApplicationCommandOption.Type.STRING, true,[
        Choice("Australia Dollar", "AUD"),
        Choice("Canada Dollar", "CAD"),
        Choice("Switzerland Franc", "CHF"),
        Choice("Denmark Krone", "DKK"),
        Choice("Euro", "EUR"),
        Choice("United Kingdom Pound", "GBP"),
        Choice("Hong Kong Dollar", "HKD"),
        Choice("Indonesia Rupiah", "IDR"),
        Choice("Israel Shekel", "ILS"),
        Choice("India Rupee", "INR"),
        Choice("Iceland Krona", "ISK"),
        Choice("Jersey Pound", "JEP"),
        Choice("Japan Yen", "JPY"),
        Choice("Korea (North) Won", "KPW"),
        Choice("Korea (South) Won", "KRW"),
        Choice("Mexico Peso", "MXN"),
        Choice("Norway Krone", "NOK"),
        Choice("New Zealand Dollar", "NZD"),
        Choice("Poland Zloty", "PLN"),
        Choice("Russia Ruble", "RUB"),
        Choice("Sweden Krona", "SEK"),
        Choice("Sierra Leone Leone", "SLL"),
        Choice("Turkey Lira", "TRY"),
        Choice("United States Dollar", "USD")
    ]),
    Option("to", "Converted Currency", ApplicationCommandOption.Type.STRING, true, [
        Choice("Australia Dollar", "AUD"),
        Choice("Canada Dollar", "CAD"),
        Choice("Switzerland Franc", "CHF"),
        Choice("Denmark Krone", "DKK"),
        Choice("Euro", "EUR"),
        Choice("United Kingdom Pound", "GBP"),
        Choice("Hong Kong Dollar", "HKD"),
        Choice("Indonesia Rupiah", "IDR"),
        Choice("Israel Shekel", "ILS"),
        Choice("India Rupee", "INR"),
        Choice("Iceland Krona", "ISK"),
        Choice("Jersey Pound", "JEP"),
        Choice("Japan Yen", "JPY"),
        Choice("Korea (North) Won", "KPW"),
        Choice("Korea (South) Won", "KRW"),
        Choice("Mexico Peso", "MXN"),
        Choice("Norway Krone", "NOK"),
        Choice("New Zealand Dollar", "NZD"),
        Choice("Poland Zloty", "PLN"),
        Choice("Russia Ruble", "RUB"),
        Choice("Sweden Krona", "SEK"),
        Choice("Sierra Leone Leone", "SLL"),
        Choice("Turkey Lira", "TRY"),
        Choice("United States Dollar", "USD")
    ])
])
class ExchangeRateCommand(private val exchangeRateService: ExchangeRateService): Command {

    override fun handle(event: ChatInputInteractionEvent): Mono<Void> {

        val amount = event.getOption("amount")
            .flatMap { obj: ApplicationCommandInteractionOption -> obj.value }
            .map { obj: ApplicationCommandInteractionOptionValue -> obj.asDouble() }
            .get()

        val from = event.getOption("from")
            .flatMap { obj: ApplicationCommandInteractionOption -> obj.value }
            .map { obj: ApplicationCommandInteractionOptionValue -> obj.asString() }
            .get()

        val to = event.getOption("to")
            .flatMap { obj: ApplicationCommandInteractionOption -> obj.value }
            .map { obj: ApplicationCommandInteractionOptionValue -> obj.asString() }
            .get()

        val exchangeRate = exchangeRateService.getExchangeRate(amount, from, to)


        val embed: EmbedCreateSpec = EmbedCreateSpec.builder()
            .color(Color.RED)
            .description("Exchange Rate")
            .addField(Currency.valueOf(from.uppercase()).toString(), amount.toString(), true)
            .addField(Currency.valueOf(to.uppercase()).toString(), String.format("%.2f", exchangeRate.rates.entries.first().value), true)
            .timestamp(Instant.now())
            .build()

        return event.reply().withEmbeds(embed)
    }
}