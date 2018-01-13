package jianxiongrao.weatherkt.data.server

import jianxiongrao.weatherkt.domain.model.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
import jianxiongrao.weatherkt.domain.model.Forecast as ModleForecast

/**
 * author: Jianxiong Rao
 * email:1272670593@qq.com
 * on 2018/1/13
 */
class ServerDataMapper{
    fun convertToDomain(zipCode:Long,forecast:ForecastResult)= with(forecast){
        ForecastList(zipCode,city.name,city.country,convertForecastListToDomain(list))
    }
    private fun convertForecastListToDomain(list:List<Forecast>):List<ModleForecast>{
         return list.mapIndexed { i, forecast ->
             val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
              convertForecastItemToDomain(forecast.copy(dt = dt))
         }
    }
    private fun convertForecastItemToDomain(forecast:Forecast) = with(forecast){
        ModleForecast(-1,dt,weather[0].description,temp.max.toInt(),temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }
    private fun generateIconUrl(iconCode:String) = "http://openweathermap.org/img/w/$iconCode.png"

}