package jianxiongrao.weatherkt.domain.commands

import jianxiongrao.weatherkt.domain.datasource.ForecastProvider
import jianxiongrao.weatherkt.domain.model.Forecast


class RequestDayForecastCommand(val id: Long, private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}