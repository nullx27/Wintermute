package tech.grimm.wintermute.services.models

import java.util.Date

data class ExchangeRate(val motd: Map<String, String>, val base: String, val date: Date, val rates: Map<String, Double>)