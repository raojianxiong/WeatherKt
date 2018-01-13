package jianxiongrao.weatherkt.domain.datasource

import jianxiongrao.weatherkt.domain.model.Forecast
import jianxiongrao.weatherkt.domain.model.ForecastList

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
interface ForecastDataSource{
    fun requestForecastByZipCode(zipCode:Long,date:Long):ForecastList?
    fun requestDayForecast(id:Long):Forecast?
}
