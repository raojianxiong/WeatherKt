package jianxiongrao.weatherkt.domain.commands


import jianxiongrao.weatherkt.domain.datasource.ForecastProvider
import jianxiongrao.weatherkt.domain.model.ForecastList

class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}