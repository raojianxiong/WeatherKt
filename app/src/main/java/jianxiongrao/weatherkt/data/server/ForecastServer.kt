package jianxiongrao.weatherkt.data.server

import jianxiongrao.weatherkt.data.db.ForecastDb
import jianxiongrao.weatherkt.domain.datasource.ForecastDataSource
import jianxiongrao.weatherkt.domain.model.Forecast
import jianxiongrao.weatherkt.domain.model.ForecastList
import java.lang.UnsupportedOperationException

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode,result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode,date)
    }

    override fun requestDayForecast(id: Long)=throw  UnsupportedOperationException()
}
