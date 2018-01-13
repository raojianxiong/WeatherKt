package jianxiongrao.weatherkt.data.db

import jianxiongrao.weatherkt.domain.model.Forecast
import jianxiongrao.weatherkt.domain.model.ForecastList

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
class DbDataMapper{
    fun convertFromDomain(forecast: ForecastList)=
        with(forecast){
            val daily = dailyForecast.map { convertDayFromDomain(id,it) }
            CityForecast(id,city,country,daily)
        }

    private fun convertDayFromDomain(cityId:Long,forecast: Forecast)=
        with(forecast){
             DayForecast(date,description,high,low,iconUrl,cityId)
        }
    fun convertToDomain(forecast:CityForecast)= with(forecast){
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id,city,country,daily)
    }
    fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast){
        Forecast(_id,date,description,high,low,iconUrl)
    }
}

